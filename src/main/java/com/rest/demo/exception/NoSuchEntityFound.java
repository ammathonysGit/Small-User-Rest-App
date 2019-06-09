package com.rest.demo.exception;

public class NoSuchEntityFound extends RuntimeException {

    public NoSuchEntityFound(String message, Throwable cause){
        super(message, cause);
    }
}
