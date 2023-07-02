package com.maradamark09.typingspeedtest.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Hidden
@Builder
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class MyErrorResponse {

    private final Integer statusCode;
    private final HttpStatus status;
    private final LocalDateTime timestamp = LocalDateTime.now();
    private final String message;
    private final String resource;
    private final List<MyFieldError> fieldErrors;

}
