package com.domanski.juniorofferproject.infrastucture.loginandregister.controller;

import javax.validation.constraints.NotBlank;

public record TokenRequest(
        @NotBlank(message = "{username.not.blank}")
        String username,

        @NotBlank(message = "{password.not.blank}")
        String password
) {
}
