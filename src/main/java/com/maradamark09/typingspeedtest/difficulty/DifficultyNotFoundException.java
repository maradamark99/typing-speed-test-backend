package com.maradamark09.typingspeedtest.difficulty;

import com.maradamark09.typingspeedtest.exception.ResourceNotFoundException;

public class DifficultyNotFoundException extends ResourceNotFoundException {
    public DifficultyNotFoundException() {
        super("difficulty");
    }
    public DifficultyNotFoundException(Long id) {
        super("difficulty", id);
    }

}
