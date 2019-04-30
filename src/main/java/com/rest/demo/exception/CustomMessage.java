package com.rest.demo.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CustomMessage {

    private Date timestamp;
    private String message;

    public CustomMessage(Date timestamp, String message){
        this.timestamp = timestamp;
        this.message = message;
    }

}
