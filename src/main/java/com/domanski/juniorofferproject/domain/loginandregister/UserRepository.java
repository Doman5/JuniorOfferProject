package com.domanski.juniorofferproject.domain.loginandregister;

import java.util.Optional;

interface UserRepository {
    Optional<User> findByUserName(String username);
    User save(User user);
}
