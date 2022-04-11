package com.mmark09.typingspeedtest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
        value = HttpStatus.CONFLICT,
        reason = "Username already exists"
)
public class UsernameAlreadyExistsException extends RuntimeException{
    public UsernameAlreadyExistsException(){}

    public UsernameAlreadyExistsException(String message){
        super(message);
    }

    public UsernameAlreadyExistsException(Throwable cause){
        super(cause);
    }
}
