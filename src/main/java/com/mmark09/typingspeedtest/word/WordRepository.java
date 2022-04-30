package com.mmark09.typingspeedtest.word;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface WordRepository extends MongoRepository <Word, String> {
    Word findAllByDifficulty(Difficulty difficulty);
}
