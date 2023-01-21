package com.maradamark09.typingspeedtest.exception;
public class ResourceAlreadyExistsException extends RuntimeException{

    public ResourceAlreadyExistsException(String resource) {
        super(buildMessageWithoutValue(resource));
    }
    public ResourceAlreadyExistsException(String resource, Object value) {
        super(buildMessageWithValue(resource, value));
    }

    private static String buildMessageWithValue(String resource, Object value) {
        return "The given " + resource + ": {" + value + "} already exists.";
    }

    private static String buildMessageWithoutValue(String resource) {
        return "The given " + resource + " already exists.";
    }

}
