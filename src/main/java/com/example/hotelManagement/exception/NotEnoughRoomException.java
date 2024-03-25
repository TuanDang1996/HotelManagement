package com.example.hotelManagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class NotEnoughRoomException extends RuntimeException {
    public NotEnoughRoomException(String message){
        super(message);
    }
}
