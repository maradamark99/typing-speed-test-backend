package com.maradamark09.typingspeedtest.difficulty;

import java.util.Collections;
import java.util.List;

class DifficultyDataProvider {

    static long DIFFICULTY_ID = 1L;

    static long DIFFICULTY_ID_TO_UPDATE = 2L;

    static DifficultyRequest VALID_DIFFICULTY_REQUEST = new DifficultyRequest("easy", (byte) 10);

    static DifficultyRequest INVALID_DIFFICULTY_REQUEST = new DifficultyRequest("a", null);

    static Difficulty DIFFICULTY_ENTITY = new Difficulty(1L, "easy", (byte) 10, Collections.emptySet());

    static List<Difficulty> LIST_OF_DIFFICULTY_RESPONSES = List.of(
            new Difficulty(1L,"easy",(byte)10, Collections.emptySet()),
            new Difficulty(2L,"medium",(byte)20, Collections.emptySet()),
            new Difficulty(3L,"hard",(byte)30, Collections.emptySet())
    );

    private DifficultyDataProvider() {

    }

}
