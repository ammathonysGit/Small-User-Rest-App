package com.rest.demo.exception;

public class EmptyCollectionException extends RuntimeException {

    public EmptyCollectionException (String message, Throwable cause){
        super(message, cause);
    }
}
