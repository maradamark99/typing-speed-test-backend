package com.maradamark09.typingspeedtest.difficulty;

import java.util.Collections;
import java.util.List;

class DifficultyDataProvider {

	static final long DIFFICULTY_ID = 1L;

	static final long DIFFICULTY_ID_TO_UPDATE = 2L;

	static final DifficultyDTO VALID_DIFFICULTY_REQUEST = DifficultyDTO.builder().value("easy")
			.maxWordLength((byte) 10).build();

	static final DifficultyDTO INVALID_DIFFICULTY_REQUEST = DifficultyDTO.builder().value("a")
			.maxWordLength(null).build();

	static final DifficultyDTO DIFFICULTY_RESPONSE = DifficultyDTO
			.builder()
			.id(1L)
			.value("easy")
			.maxWordLength((byte) 10)
			.build();

	static final List<Difficulty> LIST_OF_DIFFICULTIES = List.of(
			new Difficulty(1L, "easy", (byte) 10, Collections.emptySet()),
			new Difficulty(2L, "medium", (byte) 20, Collections.emptySet()),
			new Difficulty(3L, "hard", (byte) 30, Collections.emptySet()));

	static final List<DifficultyDTO> LIST_OF_DIFFICULTY_RESPONSES = List.of(
			DifficultyDTO
					.builder()
					.id(1L)
					.value("easy")
					.maxWordLength((byte) 10)
					.build(),
			DifficultyDTO
					.builder()
					.id(2L)
					.value("medium")
					.maxWordLength((byte) 20)
					.build(),
			DifficultyDTO
					.builder()
					.id(3L)
					.value("hard")
					.maxWordLength((byte) 30)
					.build());

	private DifficultyDataProvider() {

	}

}
