package com.maradamark09.typingspeedtest.result;

import com.maradamark09.typingspeedtest.user.User;

import java.util.List;
import java.util.UUID;

class ResultDataProvider {

    static final long VALID_RESULT_ID = 1L;

    static final long INVALID_RESULT_ID = -929L;

    static final User LOGGED_IN_USER = new User();

    static final UUID USER_ID = UUID.randomUUID();

    static final int VALID_PAGE = 1;

    static final int VALID_AMOUNT = 2;

    static final int INVALID_PAGE = -5;

    static final int INVALID_AMOUNT = -15;

    static final ResultRequest VALID_RESULT_REQUEST = new ResultRequest((short) 65, 75.55);

    static final ResultRequest INVALID_RESULT_REQUEST = new ResultRequest(null, 41232.23);

    static final List<ResultResponse> LIST_OF_RESULT_RESPONSES = List.of(
            new ResultResponse(1L, (short) 65, 89.9, "john"),
            new ResultResponse(2L, (short) 90, 95.2, "jane")
    );

    private ResultDataProvider() {

    }

}
