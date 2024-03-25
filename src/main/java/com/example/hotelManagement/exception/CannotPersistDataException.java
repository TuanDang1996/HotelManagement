package com.example.hotelManagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class CannotPersistDataException extends RuntimeException {
    public CannotPersistDataException(String message) {
        super(message);
    }
}
