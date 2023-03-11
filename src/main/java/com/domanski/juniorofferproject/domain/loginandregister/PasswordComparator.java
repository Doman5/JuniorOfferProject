package com.domanski.juniorofferproject.domain.loginandregister;

import com.domanski.juniorofferproject.domain.loginandregister.dto.RegisterCredential;

class PasswordComparator {
    boolean checkIfPasswordsAreIdentical(RegisterCredential registerCredential) {
        return registerCredential.password().equals(registerCredential.repeatPassword());
    }
}
