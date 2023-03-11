package com.domanski.juniorofferproject.domain.loginandregister;

public class IncorrectPasswordException extends RuntimeException {
    public IncorrectPasswordException(String message) {
        super(message);
    }
}
