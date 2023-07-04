package com.maradamark09.typingspeedtest.difficulty;

import com.maradamark09.typingspeedtest.exception.ResourceAlreadyExistsException;
import com.maradamark09.typingspeedtest.exception.ResourceNotFoundException;

import java.util.List;

public interface DifficultyService {

    List<DifficultyDTO> findAll();

    DifficultyDTO save(DifficultyDTO difficultyDTO) throws ResourceAlreadyExistsException;

    void deleteById(long id) throws ResourceNotFoundException;

    DifficultyDTO update(DifficultyDTO difficultyDTO, long id);

}
