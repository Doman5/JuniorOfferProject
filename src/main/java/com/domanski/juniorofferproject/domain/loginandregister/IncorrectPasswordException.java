package com.domanski.juniorofferproject.domain.loginandregister;

class IncorrectPasswordException extends RuntimeException {
    public IncorrectPasswordException(String message) {
        super(message);
    }
}
