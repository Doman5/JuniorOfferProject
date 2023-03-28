package com.domanski.juniorofferproject.infrastucture.loginandregister.controller;

import com.domanski.juniorofferproject.infrastucture.security.jwt.JwtAuthenticator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@Slf4j
public class TokenController {

    private final JwtAuthenticator jwtAuthenticator;

    @PostMapping("/token")
    public ResponseEntity<TokenResponse> generateToken(@RequestBody @Valid TokenRequest loginRequest) {
        final TokenResponse tokenResponse = jwtAuthenticator.authenticateAndGenerateToken(loginRequest);
        return ResponseEntity.ok(tokenResponse);
    }
}
