package com.maradamark09.typingspeedtest.difficulty;

import com.maradamark09.typingspeedtest.exception.ResourceNotFoundException;

public class DifficultyNotFoundException extends ResourceNotFoundException {
    private final static String resource = "difficulty";
    public DifficultyNotFoundException() {
        super(resource);
    }
    public DifficultyNotFoundException(Long id) {
        super(resource, id);
    }

}
