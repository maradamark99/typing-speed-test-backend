package com.maradamark09.typingspeedtest.difficulty;

import com.maradamark09.typingspeedtest.exception.ResourceAlreadyExistsException;

public class DifficultyAlreadyExistsException extends ResourceAlreadyExistsException {
    private final static String resource = "difficulty";
    public DifficultyAlreadyExistsException(String value){
        super(resource, value);
    }
}
