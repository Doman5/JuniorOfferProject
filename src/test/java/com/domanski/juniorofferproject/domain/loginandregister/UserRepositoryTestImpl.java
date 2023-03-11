package com.domanski.juniorofferproject.domain.loginandregister;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UserRepositoryTestImpl implements UserRepository {

    private final Map<String, User> inMemoryUserDatabase = new HashMap<>();

    @Override
    public Optional<User> findByUserName(String username) {
        if(inMemoryUserDatabase.get(username) != null) {
            return Optional.of(inMemoryUserDatabase.get(username));
        }
        return Optional.empty();
    }

    @Override
    public User save(User user) {
        inMemoryUserDatabase.put(user.username(), user);
        return user;
    }
}
