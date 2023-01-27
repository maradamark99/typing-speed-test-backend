package com.maradamark09.typingspeedtest.auth;

import com.maradamark09.typingspeedtest.exception.ResourceNotFoundException;

import java.util.UUID;

public class UserNotFoundException extends ResourceNotFoundException {

    private final static String resource = "user";

    public UserNotFoundException() {
        super(resource);
    }

    public UserNotFoundException(UUID id) {
        super(resource, id);
    }
}
