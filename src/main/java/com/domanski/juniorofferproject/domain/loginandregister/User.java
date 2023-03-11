package com.domanski.juniorofferproject.domain.loginandregister;

import lombok.Builder;

@Builder
record User(Long id,
            String username,
            String password) {
}
