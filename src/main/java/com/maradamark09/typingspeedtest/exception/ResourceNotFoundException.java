package com.maradamark09.typingspeedtest.exception;


public class ResourceNotFoundException extends RuntimeException {

    protected ResourceNotFoundException(String resource) {
        super(buildMessageWithoutId(resource));
    }
    protected ResourceNotFoundException(String resource, Object id){
        super(buildMessageWithId(resource,id));
    }

    private static String buildMessageWithId(String resource, Object id) {
        return "The given " + resource + " with id: {" + id + "} cannot be found.";
    }

    private static String buildMessageWithoutId(String resource) {
        return "The given " + resource + " cannot be found.";
    }

}
