package com.domanski.juniorofferproject.infrastucture.loginandregister.controller;

import com.domanski.juniorofferproject.domain.loginandregister.LoginAndRegisterFacade;
import com.domanski.juniorofferproject.domain.loginandregister.dto.RegisterCredential;
import com.domanski.juniorofferproject.domain.loginandregister.dto.RegisteredUserDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class RegisterController {

    private final LoginAndRegisterFacade loginAndRegisterFacade;
    private final PasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/register")
    public ResponseEntity<RegisteredUserDto> register(@RequestBody @Valid RegisterCredential registerCredential) {
        String encodedPassword = bCryptPasswordEncoder.encode(registerCredential.password());
        RegisteredUserDto register = loginAndRegisterFacade.register(new RegisterCredential(
                registerCredential.username(),
                encodedPassword));
        return ResponseEntity.status(HttpStatus.CREATED).body(register);
    }
}
