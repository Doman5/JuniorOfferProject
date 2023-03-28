package com.domanski.juniorofferproject.infrastucture.security.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(value = "app.security")
public record SecurityProperties(
        int expirationMs,
        String issuer,
        String secret
) {}


