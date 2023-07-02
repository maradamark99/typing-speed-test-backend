package com.maradamark09.typingspeedtest.difficulty;

import com.maradamark09.typingspeedtest.exception.ResourceAlreadyExistsException;

public class DifficultyAlreadyExistsException extends ResourceAlreadyExistsException {
    public DifficultyAlreadyExistsException(String value){
        super("difficulty", value);
    }

}
