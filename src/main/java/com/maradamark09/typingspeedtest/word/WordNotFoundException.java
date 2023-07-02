package com.maradamark09.typingspeedtest.word;

import com.maradamark09.typingspeedtest.exception.ResourceNotFoundException;

public class WordNotFoundException extends ResourceNotFoundException {
    public WordNotFoundException(Long id) {
        super("word", id);
    }

}
