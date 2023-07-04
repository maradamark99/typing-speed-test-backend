package com.maradamark09.typingspeedtest.word;

import com.maradamark09.typingspeedtest.difficulty.Difficulty;

import java.util.Collections;
import java.util.List;

class WordDataProvider {

    static final int AMOUNT_OF = 3;

    static final String VALID_DIFFICULTY = "easy";

    static final String INVALID_DIFFICULTY = "SOMETHING";

    static final long VALID_WORD_ID = 3L;

    static final WordDTO VALID_WORD_REQUEST = WordDTO.builder().value("that").difficulty_id(1L).build();

    static final WordDTO INVALID_WORD_REQUEST = WordDTO.builder().value(null).difficulty_id(1L).build();

    static final WordDTO WORD_REQUEST_LONG_VALUE = WordDTO.builder().value("sobasicallyimverylong").difficulty_id(1L)
            .build();

    static final Difficulty DIFFICULTY_ENTITY = new Difficulty(1L, "easy", (byte) 12, Collections.emptySet());

    static final Word WORD_ENTITY = new Word(1L, "that", DIFFICULTY_ENTITY);

    static final List<String> WORD_RESPONSE = List.of("this", "or", "that");

    private WordDataProvider() {

    }

}
