package com.phuocpt98.demo.service;


import java.text.ParseException;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.phuocpt98.demo.dto.request.AuthenticationRequest;
import com.phuocpt98.demo.dto.request.IntrospectRequest;
import com.phuocpt98.demo.dto.request.LogOutRequest;
import com.phuocpt98.demo.dto.request.RefreshRequest;
import com.phuocpt98.demo.dto.response.AuthenticationResponse;
import com.phuocpt98.demo.dto.response.IntrospectResponse;
import com.phuocpt98.demo.entity.InvalidatedToken;
import com.phuocpt98.demo.entity.User;
import com.phuocpt98.demo.exception.AppException;
import com.phuocpt98.demo.exception.ErrorCode;
import com.phuocpt98.demo.repository.InvalidatedTokenRepository;
import com.phuocpt98.demo.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {

    PasswordEncoder passwordEncoder;
    UserRepository userRepository;
    InvalidatedTokenRepository invalidatedTokenRepository;

    @NonFinal
    @Value("${jwt.signer-key}")
    String SIGNER_KEY;

    @NonFinal
    @Value("${jwt.expiration-time}")
    Long EXPIRATION_TIME;

    @NonFinal
    @Value("${jwt.refresh-expiration-time}")
    Long REFRESH_TIME;


    public AuthenticationResponse authenticated(AuthenticationRequest authenticationRequest) {
        var user = userRepository.findByUsername(authenticationRequest.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXITS));

        boolean authenticated = passwordEncoder.matches(authenticationRequest.getPassword(), user.getPassword());

        if (!authenticated) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        var token = generateToken(user);

        return AuthenticationResponse.builder().accessToken(token).authenticated(true).build();
    }

    private String generateToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("http://localhost:8080/")
                .issueTime(new Date())
                .jwtID(UUID.randomUUID().toString())
                .expirationTime(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .claim("scope", buildScope(user))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

//        String token = jwsObject.serialize();

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    private String buildScope(User user) {
        StringJoiner joiner = new StringJoiner(" ");
        if (!CollectionUtils.isEmpty(user.getRoles())) {
            user.getRoles().forEach(
                    role -> {
                        joiner.add("ROLE_" + role.getDescription());
                        if (!CollectionUtils.isEmpty(role.getPermissions())) {
                            role.getPermissions().forEach(permission -> joiner.add(permission.getDescription()));
                        }

                    }

            );
        }
        return joiner.toString();
    }

    public IntrospectResponse introspect(IntrospectRequest introspectRequest) throws JOSEException, ParseException {
        var token = introspectRequest.getToken();

        boolean isValid = true;

        try {
            verifyToken(token, false);
        } catch (AppException appException) {
            isValid = false;
        }


        return IntrospectResponse.builder().active(isValid).build();
    }


    private SignedJWT verifyToken(String token, boolean isRefresh) throws JOSEException, ParseException {

        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiration = (isRefresh) ?
                new Date(signedJWT.getJWTClaimsSet().getIssueTime().toInstant().plus(REFRESH_TIME, ChronoUnit.MILLIS).toEpochMilli())
                : signedJWT.getJWTClaimsSet().getExpirationTime();


        boolean verify = signedJWT.verify(verifier);
        boolean active = expiration.after(new Date());
        if (!active || !verify) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        String jit = signedJWT.getJWTClaimsSet().getJWTID();

        if (invalidatedTokenRepository.existsById(jit)) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
        return signedJWT;
    }

    public void logout(LogOutRequest logOutRequest) throws JOSEException, ParseException {
        var token = logOutRequest.getToken();

        try {
            var jwtToken = verifyToken(token, true);


            String jit = jwtToken.getJWTClaimsSet().getJWTID();
            Date expiryTime = jwtToken.getJWTClaimsSet().getExpirationTime();

            InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                    .id(jit)
                    .expiryTime(expiryTime)
                    .build();
            invalidatedTokenRepository.save(invalidatedToken);
        } catch (AppException e) {
            log.info("Token is invalid");
        }

    }


    public AuthenticationResponse refreshToken(RefreshRequest request) throws JOSEException, ParseException {
        // kiem tra hieu luc token
        var signJWT = verifyToken(request.getToken(), true);
        String username = signJWT.getJWTClaimsSet().getSubject();

        String jit = signJWT.getJWTClaimsSet().getJWTID();
        Date expiryTime = signJWT.getJWTClaimsSet().getExpirationTime();

        InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                .id(jit)
                .expiryTime(expiryTime)
                .build();
        invalidatedTokenRepository.save(invalidatedToken);

        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXITS));

        var token = generateToken(user);

        return AuthenticationResponse.builder().accessToken(token).authenticated(true).build();
    }
}
