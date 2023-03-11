package com.domanski.juniorofferproject.domain.loginandregister;

import com.domanski.juniorofferproject.domain.loginandregister.dto.RegisterCredential;
import com.domanski.juniorofferproject.domain.loginandregister.dto.UserDto;
import lombok.AllArgsConstructor;

@AllArgsConstructor
class UserMapper {

    public static UserDto mapFromUser(User user) {
        return UserDto.builder()
                .username(user.username())
                .build();
    }

    public static User mapFromRegisterCredentials(RegisterCredential registerCredential) {
        return User.builder()
                .username(registerCredential.username())
                .password(registerCredential.password())
                .build();
    }
}
