package com.maradamark09.typingspeedtest.word;

import com.maradamark09.typingspeedtest.exception.ResourceAlreadyExistsException;

public class WordAlreadyExistsException extends ResourceAlreadyExistsException {
    public WordAlreadyExistsException(String value) {
        super("word", value);
    }
}
