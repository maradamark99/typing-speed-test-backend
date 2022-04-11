package com.mmark09.typingspeedtest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
        value = HttpStatus.UNPROCESSABLE_ENTITY,
        reason = "Not valid input given"
)
public class InvalidRegistrationRequestException extends RuntimeException{

    public InvalidRegistrationRequestException(){}

    public InvalidRegistrationRequestException(String message) {
        super(message);
    }
    public InvalidRegistrationRequestException(Throwable t){
        super(t);
    }
}
