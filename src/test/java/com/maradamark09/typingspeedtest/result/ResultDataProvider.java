package com.maradamark09.typingspeedtest.result;

import com.maradamark09.typingspeedtest.difficulty.Difficulty;
import com.maradamark09.typingspeedtest.user.User;
import com.maradamark09.typingspeedtest.util.PageResponse;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

class ResultDataProvider {

	static final Long VALID_DATE = 1689777436000L;

	static final long VALID_RESULT_ID = 1L;

	static final long INVALID_RESULT_ID = -929L;

	static final User LOGGED_IN_USER = new User();

	static final UUID USER_ID = UUID.randomUUID();

	static final int VALID_PAGE = 1;

	static final int VALID_SIZE = 2;

	static final int INVALID_PAGE = -5;

	static final int INVALID_SIZE = -15;

	static final ResultDTO VALID_RESULT_DTO = ResultDTO
			.builder()
			.wpm((short) 65)
			.accuracy(75.55)
			.difficulty("EASY")
			.date(VALID_DATE)
			.build();

	static final Result RESULT_ENTITY = Result
			.builder()
			.wpm((short) 65)
			.accuracy(BigDecimal.valueOf(75.55))
			.difficulty(
					Difficulty
							.builder()
							.id(1L)
							.value("EASY")
							.maxWordLength((byte) 8)
							.build())
			.date(Timestamp.from(Instant.ofEpochMilli(VALID_DATE)))
			.user(LOGGED_IN_USER)
			.build();

	static final ResultDTO INVALID_RESULT_DTO = ResultDTO.builder().wpm(null).accuracy(41232.23).difficulty("ASD")
			.build();

	static final List<Result> LIST_OF_RESULT_ENTITIES = List.of(
			Result.builder()
					.id(1L)
					.wpm((short) 65)
					.accuracy(BigDecimal.valueOf(89.9))
					.user(
							User.builder()
									.username("john")
									.build())
					.difficulty(
							Difficulty.builder()
									.id(1L)
									.value("EASY")
									.maxWordLength((byte) 8)
									.build())
					.date(Timestamp.from(Instant.ofEpochMilli(VALID_DATE)))
					.build(),
			Result.builder()
					.id(2L)
					.wpm((short) 90)
					.accuracy(BigDecimal.valueOf(95.2))
					.user(
							User.builder()
									.username("jane")
									.build())
					.difficulty(
							Difficulty.builder()
									.id(2L)
									.value("MEDIUM")
									.maxWordLength((byte) 12)
									.build())
					.date(Timestamp.from(Instant.ofEpochMilli(VALID_DATE)))
					.build());

	static final List<ResultDTO> LIST_OF_RESULT_DTO = List.of(
			ResultDTO
					.builder()
					.id(1L)
					.wpm((short) 65)
					.accuracy(89.9)
					.difficulty("EASY")
					.username("john")
					.date(VALID_DATE)
					.build(),
			ResultDTO
					.builder()
					.id(2L)
					.wpm((short) 90)
					.accuracy(95.2)
					.difficulty("MEDIUM")
					.username("jane")
					.date(VALID_DATE)
					.build());

	static final PageResponse<ResultDTO> PAGE_OF_RESULT_DTO = new PageResponse<>(List.of(
			ResultDTO
					.builder()
					.id(1L)
					.wpm((short) 65)
					.accuracy(89.9)
					.username("john")
					.date(VALID_DATE)
					.build(),
			ResultDTO
					.builder()
					.id(2L)
					.wpm((short) 90)
					.accuracy(95.2)
					.username("jane")
					.date(VALID_DATE)
					.build()),
			1, 2, 0, true, true);

	private ResultDataProvider() {

	}

}
