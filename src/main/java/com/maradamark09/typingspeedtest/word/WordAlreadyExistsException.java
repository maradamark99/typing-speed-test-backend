package com.maradamark09.typingspeedtest.word;

import com.maradamark09.typingspeedtest.exception.ResourceAlreadyExistsException;

public class WordAlreadyExistsException extends ResourceAlreadyExistsException {

    private static final String resource = "word";
    public WordAlreadyExistsException(String value) {
        super(resource, value);
    }
}
