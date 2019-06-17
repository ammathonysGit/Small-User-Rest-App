package com.rest.demo.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.util.Date;

@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {
    //By extending the ResponseEntityExceptionHandler I can handle all the exceptions that Spring throws in the way I want just by overriding the methods for the exceptions.

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
//        return new ResponseEntity(new CustomMessage(new Date(), ex.getMessage()), HttpStatus.CONFLICT);
        return new ResponseEntity(new CustomMessage(new Date(), "INVALID INPUT FOR THE FIELDS "), HttpStatus.CONFLICT);
    }

//handle request param validation exception
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity(new CustomMessage(new Date(), "Invalid search parameters brand or model.! "), HttpStatus.CONFLICT);
    }

// BoatController may throw NoSuchEntityFound Exception and by extending ResponseEntityExceptionHandler I can handle it here in my custom method(handler
    //I just need to give the method @ExceptionHandler and value = class exception i want to handle.
    @ExceptionHandler(value = {NoSuchEntityFound.class})
    public ResponseEntity handleNoSuchEntityFoundException(NoSuchEntityFound noSuchEntityFound, WebRequest webRequest) {
        return new ResponseEntity(new CustomMessage(new Date(),noSuchEntityFound.getLocalizedMessage()), HttpStatus.NOT_FOUND);
    }


//If I have a custom exception i can catch it and do something like the example bellow.
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleAnyException(Exception e, WebRequest webRequest) {
        CustomMessage customMessage = new CustomMessage(new Date(), e.getMessage());
        return new ResponseEntity<>(customMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
