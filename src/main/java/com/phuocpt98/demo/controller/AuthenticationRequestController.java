package com.phuocpt98.demo.controller;

import com.nimbusds.jose.JOSEException;
import com.phuocpt98.demo.ApiResponse.ApiResponse;
import com.phuocpt98.demo.dto.request.AuthenticationRequest;
import com.phuocpt98.demo.dto.request.IntrospectRequest;
import com.phuocpt98.demo.dto.response.AuthenticationResponse;
import com.phuocpt98.demo.dto.response.IntrospectResponse;
import com.phuocpt98.demo.service.AuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationRequestController {
    AuthenticationService authenticationService;

    @PostMapping("/login")
    public ApiResponse<AuthenticationResponse> login(@RequestBody AuthenticationRequest authenticationRequest) {
        return ApiResponse.success(authenticationService.authenticated(authenticationRequest));
    }

    @PostMapping("/introspect")
    public ApiResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest introspectRequest) throws ParseException, JOSEException {
        return ApiResponse.success(authenticationService.introspect(introspectRequest));
    }
}
