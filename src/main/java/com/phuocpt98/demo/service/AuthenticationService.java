package com.phuocpt98.demo.service;


import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.phuocpt98.demo.dto.request.AuthenticationRequest;
import com.phuocpt98.demo.dto.request.IntrospectRequest;
import com.phuocpt98.demo.dto.response.AuthenticationResponse;
import com.phuocpt98.demo.dto.response.IntrospectResponse;
import com.phuocpt98.demo.exception.AppException;
import com.phuocpt98.demo.exception.ErrorCode;
import com.phuocpt98.demo.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.logging.Level;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {

    UserRepository userRepository;

    @NonFinal
    @Value("${jwt.signerKey}")
    String SIGNER_KEY;

    public AuthenticationResponse authenticated(AuthenticationRequest authenticationRequest){
        var user = userRepository.findByUsername(authenticationRequest.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXITS));

        PasswordEncoder encoder = new BCryptPasswordEncoder(10);
        boolean authenticated =  encoder.matches(authenticationRequest.getPassword(), user.getPassword());
        if(!authenticated){
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        var token = generateToken(authenticationRequest.getUsername());

        return AuthenticationResponse.builder().accessToken(token).authenticated(true).build();
    }

    private String generateToken(String username){
        JWSHeader header= new JWSHeader(JWSAlgorithm.HS256);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(username)
                .issuer("http://localhost:8080/")
                .issueTime(new Date())
                .expirationTime(new Date(System.currentTimeMillis() + 60 * 60 * 1000))
                .claim("role", "USER")
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject   = new JWSObject(header, payload);

//        String token = jwsObject.serialize();

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    public IntrospectResponse introspect(IntrospectRequest introspectRequest) throws JOSEException, ParseException {
        var token = introspectRequest.getToken();

            JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
            SignedJWT signedJWT = SignedJWT.parse(token);

            Date expiration = signedJWT.getJWTClaimsSet().getExpirationTime();
            boolean verify = signedJWT.verify(verifier);
            boolean active = expiration.after(new Date());
        return IntrospectResponse.builder().active(verify && active).build();
    }
}
