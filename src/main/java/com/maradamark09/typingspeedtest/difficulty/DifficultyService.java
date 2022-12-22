package com.maradamark09.typingspeedtest.difficulty;

import com.maradamark09.typingspeedtest.exception.ResourceAlreadyExistsException;
import com.maradamark09.typingspeedtest.exception.ResourceNotFoundException;

import java.util.List;

public interface DifficultyService {
    List<Difficulty> findAll();

    Difficulty save(DifficultyRequest difficultyRequest) throws ResourceAlreadyExistsException;

    void deleteById(Long id) throws ResourceNotFoundException;

    Difficulty update(DifficultyRequest difficultyRequest, Long id);

}
