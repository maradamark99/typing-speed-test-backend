package com.maradamark09.typingspeedtest.auth;

import com.maradamark09.typingspeedtest.exception.ResourceAlreadyExistsException;

public class UserAlreadyExistsException extends ResourceAlreadyExistsException {
    private static final String resource = "user";
    public UserAlreadyExistsException() {
        super(resource);
    }
}
