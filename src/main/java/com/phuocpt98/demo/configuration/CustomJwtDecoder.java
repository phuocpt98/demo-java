package com.phuocpt98.demo.configuration;

import java.text.ParseException;
import java.util.Objects;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

import com.nimbusds.jose.JOSEException;
import com.phuocpt98.demo.dto.request.IntrospectRequest;
import com.phuocpt98.demo.service.AuthenticationService;

@Component
@Configuration
public class CustomJwtDecoder implements JwtDecoder {
    @Value("${jwt.signer-key}")
    private String signerKey;

    @Autowired
    private AuthenticationService authenticationService;

    private NimbusJwtDecoder nimbusJwtDecoder = null;

    @Override
    public Jwt decode(String token) throws JwtException {

        try {
           var response =  authenticationService.introspect(IntrospectRequest.builder().token(token).build());
           if(!response.isActive()){
               throw new JwtException("Token is invalid");
           }

        } catch (JOSEException | ParseException e) {
            throw new JwtException(e.getMessage());
        }

        if(Objects.isNull(nimbusJwtDecoder)){
            SecretKeySpec secretKey = new SecretKeySpec(signerKey.getBytes(), "HS256");
            nimbusJwtDecoder = NimbusJwtDecoder.withSecretKey(secretKey)
                    .macAlgorithm(MacAlgorithm.HS256)
                    .build();
        }

        System.out.println(nimbusJwtDecoder);
        return nimbusJwtDecoder.decode(token);
    }
}
