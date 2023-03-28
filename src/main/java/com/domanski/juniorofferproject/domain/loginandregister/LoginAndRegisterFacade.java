package com.domanski.juniorofferproject.domain.loginandregister;

import com.domanski.juniorofferproject.domain.loginandregister.dto.RegisterCredential;
import com.domanski.juniorofferproject.domain.loginandregister.dto.RegisteredUserDto;
import com.domanski.juniorofferproject.domain.loginandregister.dto.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class LoginAndRegisterFacade {

    public static final boolean CREATED_USER = true;
    private UserRepository userRepository;

    public UserDto findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(UserMapper::mapFromUser)
                .orElseThrow(() -> new BadCredentialsException("User not found!"));
    }

    public RegisteredUserDto register(RegisterCredential registerCredential) {
        User userToSave = UserMapper.mapFromRegisterCredentials(registerCredential);
        User savedUser = userRepository.save(userToSave);
        return new RegisteredUserDto(savedUser.id(), CREATED_USER, savedUser.username());
    }
}
