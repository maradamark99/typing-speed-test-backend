package com.mmark09.typingspeedtest.word;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface WordRepository extends MongoRepository <Word, String> {
    List<Word> findAllByDifficulty(String difficulty);
}
