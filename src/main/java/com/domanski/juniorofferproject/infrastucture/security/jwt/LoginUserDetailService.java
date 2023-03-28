package com.domanski.juniorofferproject.infrastucture.security.jwt;

import com.domanski.juniorofferproject.domain.loginandregister.LoginAndRegisterFacade;
import com.domanski.juniorofferproject.domain.loginandregister.dto.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Collections;

@AllArgsConstructor
public class LoginUserDetailService implements UserDetailsService {

    private final LoginAndRegisterFacade loginAndRegisterFacade;

    @Override
    public UserDetails loadUserByUsername(String username) throws BadCredentialsException {
        UserDto userDto = loginAndRegisterFacade.findUserByUsername(username);
        return getUser(userDto);
    }

    private User getUser(UserDto userDto) {
        return new User(userDto.username(),
                userDto.password(),
                Collections.emptyList());
    }
}
