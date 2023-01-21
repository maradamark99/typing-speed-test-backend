package com.maradamark09.typingspeedtest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class RestControllerExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public MyErrorResponse handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {

        final var status = HttpStatus.NOT_FOUND;

        return MyErrorResponse.builder()
                .status(status)
                .statusCode(status.value())
                .message(ex.getMessage())
                .resource(request.getDescription(false))
                .build();
    }

    @ExceptionHandler({
            HttpMessageNotReadableException.class,
            MethodArgumentTypeMismatchException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MyErrorResponse handleInvalidRequestBodyFormatException(Exception ex, WebRequest request){

        final var status = HttpStatus.BAD_REQUEST;

        return MyErrorResponse.builder()
                .status(status)
                .statusCode(status.value())
                .message(ex.getMessage())
                .resource(request.getDescription(false))
                .build();
    }

    @ExceptionHandler({
            WordLengthGreaterThanDifficultyException.class,
    })
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public MyErrorResponse handleInvalidWordLengthGreaterThanDifficultyException(
            Exception ex, WebRequest request) {

        final var status = HttpStatus.UNPROCESSABLE_ENTITY;

        return MyErrorResponse.builder()
                .status(status)
                .statusCode(status.value())
                .message(ex.getMessage())
                .resource(request.getDescription(false))
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public MyErrorResponse handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex, WebRequest request) {

        final var status = HttpStatus.UNPROCESSABLE_ENTITY;

        return MyErrorResponse.builder()
                .status(status)
                .statusCode(status.value())
                .resource(request.getDescription(false))
                .fieldErrors(getUsefulInfoFromFieldErrors(ex.getFieldErrors()))
                .build();
    }

    public List<MyFieldError> getUsefulInfoFromFieldErrors(List<FieldError> fieldErrors) {
        final var myFieldErrors = new ArrayList<MyFieldError>();
        fieldErrors.forEach(x ->
                myFieldErrors.add(
                        new MyFieldError(x.getField(), x.getDefaultMessage())
                )
        );
        return myFieldErrors;
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public MyErrorResponse handleResourceAlreadyExistsException(
            ResourceAlreadyExistsException ex,
            WebRequest request) {

        final var status = HttpStatus.CONFLICT;

        return MyErrorResponse.builder()
                .status(status)
                .statusCode(status.value())
                .message(ex.getMessage())
                .resource(request.getDescription(false))
                .build();
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public MyErrorResponse handleBadCredentialsException(Exception ex, WebRequest request) {
        final var status = HttpStatus.UNAUTHORIZED;

        return MyErrorResponse.builder()
                .status(status)
                .statusCode(status.value())
                .message(ex.getMessage())
                .resource(request.getDescription(false))
                .build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public MyErrorResponse handleGlobalException(Exception ex, WebRequest request) {

        final var status = HttpStatus.INTERNAL_SERVER_ERROR;

        return MyErrorResponse.builder()
                .status(status)
                .statusCode(status.value())
                .message(ex.getMessage())
                .resource(request.getDescription(false))
                .build();
    }


}