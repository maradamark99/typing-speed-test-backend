package com.maradamark09.typingspeedtest.difficulty;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DifficultyRepository extends JpaRepository<Difficulty, Long> {
    Boolean existsByValue(String difficulty);

}
