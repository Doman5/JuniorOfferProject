package com.domanski.juniorofferproject.domain.loginandregister;

class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String message) {
        super(message);
    }
}
