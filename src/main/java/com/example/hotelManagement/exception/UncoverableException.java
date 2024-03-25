package com.example.hotelManagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class UncoverableException extends RuntimeException {
    public UncoverableException(String message){
        super(message);
    }
}
