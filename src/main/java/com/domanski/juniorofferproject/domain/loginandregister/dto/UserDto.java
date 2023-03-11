package com.domanski.juniorofferproject.domain.loginandregister.dto;

import lombok.Builder;

@Builder
public record UserDto(Long id,
                      String username) {}
