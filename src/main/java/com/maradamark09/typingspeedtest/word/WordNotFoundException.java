package com.maradamark09.typingspeedtest.word;

import com.maradamark09.typingspeedtest.exception.ResourceNotFoundException;

public class WordNotFoundException extends ResourceNotFoundException {
    private final static String resource = "word";
    public WordNotFoundException(Long id) {
        super(resource, id);
    }

}
