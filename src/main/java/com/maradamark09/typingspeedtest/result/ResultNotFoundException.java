package com.maradamark09.typingspeedtest.result;

import com.maradamark09.typingspeedtest.exception.ResourceNotFoundException;

public class ResultNotFoundException extends ResourceNotFoundException {
    public ResultNotFoundException() {
        super("result");
    }

    public ResultNotFoundException(Long id) {
        super("result", id);
    }
}
