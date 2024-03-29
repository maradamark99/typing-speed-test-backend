package com.maradamark09.typingspeedtest.word;

import com.maradamark09.typingspeedtest.exception.ResourceAlreadyExistsException;
import com.maradamark09.typingspeedtest.exception.ResourceNotFoundException;

import java.util.List;

public interface WordService {

    List<String> getAllByDifficulty(String difficulty) throws ResourceNotFoundException;

    List<String> getRandomWordsByDifficulty(String difficulty, Integer amount) throws ResourceNotFoundException;

    WordDTO save(WordDTO wordDTO) throws ResourceNotFoundException,
            WordLengthGreaterThanDifficultyException,
            ResourceAlreadyExistsException;

    void deleteById(Long id) throws ResourceNotFoundException;

}
