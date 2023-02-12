package com.maradamark09.typingspeedtest.word;

import com.maradamark09.typingspeedtest.difficulty.Difficulty;

import java.util.Collections;
import java.util.List;

class WordDataProvider {

    static final String VALID_DIFFICULTY = "easy";

    static final String INVALID_DIFFICULTY = "SOMETHING";

    static final long VALID_WORD_ID = 3L;

    static final WordRequest VALID_WORD_REQUEST = new WordRequest("that", 1L);

    static final WordRequest INVALID_WORD_REQUEST = new WordRequest(null, 1L);

    static final WordRequest WORD_REQUEST_LONG_VALUE = new WordRequest("sobasicallyimverylong", 1L);

    static final Difficulty DIFFICULTY_ENTITY = new Difficulty(1L, "easy", (byte)12, Collections.emptySet());

    static final Word WORD_ENTITY = new Word(1L, "that", DIFFICULTY_ENTITY);

    static final List<String> WORD_RESPONSE = List.of("this", "or", "that");

    private WordDataProvider() {

    }

}
