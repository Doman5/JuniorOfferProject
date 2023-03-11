package com.domanski.juniorofferproject.domain.loginandregister;

import com.domanski.juniorofferproject.domain.loginandregister.dto.RegisterCredential;
import com.domanski.juniorofferproject.domain.loginandregister.dto.UserDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LoginAndRegisterFacadeTest {

    LoginAndRegisterFacade loginAndRegisterFacade = new LoginAndRegisterFacade(
            new UserRepositoryTestImpl(),
            new PasswordComparator()
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
    public void should_throw_incorrect_password_exception_when_user_gave_not_identical_password() {
        //given
        RegisterCredential userToRegister = prepareIncorrectUserRegisterData();
        // ...
        assertThrows(IncorrectPasswordException.class, () -> loginAndRegisterFacade.register(userToRegister), "Password are not identical!");
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
        assertThrows(UserNotFoundException.class,() -> loginAndRegisterFacade.findUserByUsername(givenUserName), "User not found!");
    }

    private static RegisterCredential prepareCorrectRegisterCredentials() {
        return RegisterCredential.builder()
                .username("Username")
                .password("pass")
                .repeatPassword("pass")
                .build();
    }


    private static RegisterCredential prepareIncorrectUserRegisterData() {
        return RegisterCredential.builder()
                .username("User")
                .password("pass")
                .repeatPassword("passs")
                .build();
    }

}