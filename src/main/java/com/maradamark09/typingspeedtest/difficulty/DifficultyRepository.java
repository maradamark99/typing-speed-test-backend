package com.maradamark09.typingspeedtest.difficulty;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DifficultyRepository extends JpaRepository<Difficulty, Long> {
    boolean existsByValueIgnoreCase(String difficulty);

    Optional<Difficulty> findByValueIgnoreCase(String difficulty);
}
