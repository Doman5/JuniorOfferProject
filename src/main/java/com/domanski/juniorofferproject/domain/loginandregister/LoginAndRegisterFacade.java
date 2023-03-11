package com.domanski.juniorofferproject.domain.loginandregister;

import com.domanski.juniorofferproject.domain.loginandregister.dto.RegisterCredential;
import com.domanski.juniorofferproject.domain.loginandregister.dto.RegisteredUserDto;
import com.domanski.juniorofferproject.domain.loginandregister.dto.UserDto;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LoginAndRegisterFacade {

    public static final boolean CREATED_USER = true;
    private UserRepository userRepository;
    private PasswordComparator passwordChecker;

    public UserDto findUserByUsername(String username) {
        return userRepository.findByUserName(username)
                .map(UserMapper::mapFromUser)
                .orElseThrow(() -> new UserNotFoundException("User not found!"));
    }

    public RegisteredUserDto register(RegisterCredential registerCredential) {
        if(!passwordChecker.checkIfPasswordsAreIdentical(registerCredential)) {
            throw new IncorrectPasswordException("Password are not identical!");
        }
        User userToSave = UserMapper.mapFromRegisterCredentials(registerCredential);
        User savedUser = userRepository.save(userToSave);
        return new RegisteredUserDto(savedUser.id(), CREATED_USER, savedUser.username());
    }
}
