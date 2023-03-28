package com.domanski.juniorofferproject.domain.loginandregister;

import com.domanski.juniorofferproject.domain.loginandregister.dto.RegisterCredential;
import com.domanski.juniorofferproject.domain.loginandregister.dto.RegisteredUserDto;
import com.domanski.juniorofferproject.domain.loginandregister.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.BadCredentialsException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LoginAndRegisterFacadeTest {

    LoginAndRegisterFacade loginAndRegisterFacade = new LoginAndRegisterFacade(
            new UserRepositoryTestImpl()
    );

    @Test
    public void should_register_user_with_correct_data() {
        //given
        RegisterCredential userToRegister = prepareCorrectRegisterCredentials();
        //when
        RegisteredUserDto result = loginAndRegisterFacade.register(userToRegister);
        //then
        assertThat(result.username()).isEqualTo("Username");
        assertThat(result.created()).isTrue();
    }

    @Test
    public void should_find_user_by_username() {
        //given
        String givenUserName = "Username";
        loginAndRegisterFacade.register(prepareCorrectRegisterCredentials());
        //when
        UserDto result = loginAndRegisterFacade.findUserByUsername(givenUserName);
        //then
        assertThat(result.username()).isEqualTo("Username");
    }

    @Test
    public void should_throw_user_not_found_exception_when_user_no_exist_in_database() {
        //given
        String givenUserName = "Username";
        // ...
        assertThrows(BadCredentialsException.class,() -> loginAndRegisterFacade.findUserByUsername(givenUserName), "User not found!");
    }

    private static RegisterCredential prepareCorrectRegisterCredentials() {
        return RegisterCredential.builder()
                .username("Username")
                .password("pass")
                .build();
    }

}