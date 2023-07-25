package com.maradamark09.typingspeedtest.word;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WordRepository extends JpaRepository<Word, Long> {

        Boolean existsByValue(String value);

        @Query(value = "SELECT w.value " +
                        "FROM Word w " +
                        "JOIN Difficulty d " +
                        "ON w.difficulty.id=d.id " +
                        "WHERE d.value=:difficulty " +
                        "ORDER BY RANDOM()")
        List<String> findRandomWordsByDifficulty(
                        @Param("difficulty") String difficulty,
                        Pageable p);

        @Query(value = "SELECT w.value " +
                        "FROM Word w " +
                        "JOIN Difficulty d " +
                        "ON w.difficulty.id=d.id " +
                        "WHERE d.value=:difficulty")
        List<String> findAllByDifficulty(@Param("difficulty") String difficulty);

}
