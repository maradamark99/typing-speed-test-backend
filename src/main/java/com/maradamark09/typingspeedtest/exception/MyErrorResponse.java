package com.maradamark09.typingspeedtest.exception;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Hidden
public class MyErrorResponse {

    private Integer statusCode;
    private HttpStatus status;
    private LocalDateTime timestamp;
    private String message;
    private String resource;

    private MyErrorResponse() {
        this.timestamp = LocalDateTime.now();
    }

    public MyErrorResponse(HttpStatus status){
        this();
        this.statusCode = status.value();
        this.status = status;
    }

    public MyErrorResponse(HttpStatus status, String message) {
        this(status);
        this.message = message;
    }

    public MyErrorResponse(HttpStatus status, String message, String resource) {
        this(status, message);
        this.resource = resource;
    }

}
