package com.domanski.juniorofferproject.infrastucture.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.domanski.juniorofferproject.infrastucture.loginandregister.controller.TokenRequest;
import com.domanski.juniorofferproject.infrastucture.loginandregister.controller.TokenResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@AllArgsConstructor
@Component
public class JwtAuthenticator {

    private SecurityProperties securityProperties;
    private final AuthenticationManager authenticationManager;
    private final Clock clock;

    public TokenResponse authenticateAndGenerateToken(TokenRequest tokenRequest) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(tokenRequest.username(), tokenRequest.password()));
        User user = (User) authenticate.getPrincipal();
        String token = createToken(user);
        String username = user.getUsername();
        return TokenResponse.builder()
                .username(username)
                .token(token)
                .build();
    }

    private String createToken(User user) {
        String secret = securityProperties.secret();
        int expirationMs = securityProperties.expirationMs();
        String issuer = securityProperties.issuer();

        Algorithm algorithm = Algorithm.HMAC256(secret);
        Instant now = LocalDateTime.now(clock).toInstant(ZoneOffset.UTC);
        Instant expiresAt = now.plusMillis(expirationMs);
        return JWT.create()
                .withSubject(user.getUsername())
                .withIssuer(issuer)
                .withIssuedAt(now)
                .withExpiresAt(expiresAt)
                .withIssuer("auth0")
                .sign(algorithm);
    }
}
