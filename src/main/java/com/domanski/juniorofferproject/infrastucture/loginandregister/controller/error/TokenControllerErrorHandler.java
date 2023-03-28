package com.domanski.juniorofferproject.infrastucture.loginandregister.controller.error;

import com.domanski.juniorofferproject.infrastucture.loginandregister.controller.TokenController;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice(basePackageClasses = {TokenController.class})
public class TokenControllerErrorHandler {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(BadCredentialsException.class)
    @ResponseBody
    public TokenErrorResponse handleBadCredentialsException() {
        return TokenErrorResponse.builder()
                .message("Bad credentials")
                .status(HttpStatus.UNAUTHORIZED)
                .build();
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseBody
    public TokenErrorResponse handleUnauthorizedException() {
        return TokenErrorResponse.builder()
                .message("User is already exist")
                .status(HttpStatus.CONFLICT)
                .build();

    }
}
