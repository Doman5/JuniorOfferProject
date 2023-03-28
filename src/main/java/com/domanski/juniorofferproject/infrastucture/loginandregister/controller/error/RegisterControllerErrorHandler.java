package com.domanski.juniorofferproject.infrastucture.loginandregister.controller.error;

import com.domanski.juniorofferproject.infrastucture.loginandregister.controller.RegisterController;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice(basePackageClasses = {RegisterController.class})
public class RegisterControllerErrorHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DuplicateKeyException.class)
    public RegisterErrorResponse handleDuplicateKeyException() {
        return RegisterErrorResponse.builder()
                .message("User is already exist")
                .status(HttpStatus.CONFLICT)
                .build();
    }
}
