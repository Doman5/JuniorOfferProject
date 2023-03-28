package com.domanski.juniorofferproject.domain.loginandregister.dto;

import lombok.Builder;

import javax.validation.constraints.NotBlank;

@Builder
public record RegisterCredential(@NotBlank(message = "{username.not.blank}")
                                 String username,

                                 @NotBlank(message = "{password.not.blank}")
                                 String password) {
}
