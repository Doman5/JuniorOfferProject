package com.domanski.juniorofferproject.domain.loginandregister.dto;

public record RegisteredUserDto(Long id,
                                boolean created,
                                String username) {
}
