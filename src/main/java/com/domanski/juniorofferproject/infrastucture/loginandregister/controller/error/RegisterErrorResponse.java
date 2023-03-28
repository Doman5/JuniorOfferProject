package com.domanski.juniorofferproject.infrastucture.loginandregister.controller.error;

import lombok.Builder;
import org.springframework.http.HttpStatus;

@Builder
public record RegisterErrorResponse(
        String message,
        HttpStatus status
) {
}
