package com.maradamark09.typingspeedtest.result;

import com.maradamark09.typingspeedtest.user.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

class ResultDataProvider {

	static final long VALID_RESULT_ID = 1L;

	static final long INVALID_RESULT_ID = -929L;

	static final User LOGGED_IN_USER = new User();

	static final UUID USER_ID = UUID.randomUUID();

	static final int VALID_PAGE = 1;

	static final int VALID_SIZE = 2;

	static final int INVALID_PAGE = -5;

	static final int INVALID_SIZE = -15;

	static final ResultDTO VALID_RESULT_DTO = ResultDTO.builder().wpm((short) 65).accuracy(75.55).build();

	static final ResultDTO INVALID_RESULT_DTO = ResultDTO.builder().wpm(null).accuracy(41232.23).build();

	static final List<Result> LIST_OF_RESULT_ENTITIES = List.of(
			Result.builder()
					.id(1L)
					.wpm((short) 65)
					.accuracy(BigDecimal.valueOf(89.9))
					.user(
							User.builder()
									.username("john")
									.build())
					.build(),
			Result.builder()
					.id(2L)
					.wpm((short) 90)
					.accuracy(BigDecimal.valueOf(95.2))
					.user(
							User.builder()
									.username("jane")
									.build())
					.build());

	static final List<ResultDTO> LIST_OF_RESULT_DTO = List.of(
			ResultDTO
					.builder()
					.id(1L)
					.wpm((short) 65)
					.accuracy(89.9)
					.username("john")
					.build(),
			ResultDTO
					.builder()
					.id(2L)
					.wpm((short) 90)
					.accuracy(95.2)
					.username("jane")
					.build());

	private ResultDataProvider() {

	}

}
