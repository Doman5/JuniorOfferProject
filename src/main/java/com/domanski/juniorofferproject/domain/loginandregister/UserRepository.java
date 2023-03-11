package com.domanski.juniorofferproject.domain.loginandregister;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByUserName(String username);
    User save(User user);
}
