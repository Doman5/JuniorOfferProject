package com.domanski.juniorofferproject.domain.loginandregister;

public record RegisteredUserDto(Long id,
                                boolean created,
                                String username) {
}
