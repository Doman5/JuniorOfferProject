package com.domanski.juniorofferproject.infrastucture.loginandregister.controller;

import lombok.Builder;

@Builder
public record TokenResponse(
        String username,
        String token
) {
}
