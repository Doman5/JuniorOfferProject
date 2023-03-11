package com.domanski.juniorofferproject.domain.loginandregister.dto;

import lombok.Builder;

@Builder
public record RegisterCredential(String username,
                                 String password,
                                 String repeatPassword) {
}
