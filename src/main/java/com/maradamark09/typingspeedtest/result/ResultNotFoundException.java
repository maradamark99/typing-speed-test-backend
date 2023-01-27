package com.maradamark09.typingspeedtest.result;

import com.maradamark09.typingspeedtest.exception.ResourceNotFoundException;

public class ResultNotFoundException extends ResourceNotFoundException {

    private final static String resource = "result";

    public ResultNotFoundException() {
        super(resource);
    }

    public ResultNotFoundException(Long id) {
        super(resource, id);
    }
}
