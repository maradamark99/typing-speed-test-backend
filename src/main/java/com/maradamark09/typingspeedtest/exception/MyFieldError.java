package com.maradamark09.typingspeedtest.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MyFieldError {
    private String field;
    private String errorMessage;
}
