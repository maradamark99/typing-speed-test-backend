package com.maradamark09.typingspeedtest.difficulty;

import java.util.List;

public interface DifficultyService {
    List<Difficulty> findAll();

    Difficulty save(DifficultyRequest difficultyRequest);

    void deleteById(Long id);

    Difficulty update(DifficultyRequest difficultyRequest, Long id);

}
