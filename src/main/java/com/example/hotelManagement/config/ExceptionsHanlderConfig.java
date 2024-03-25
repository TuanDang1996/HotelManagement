package com.example.hotelManagement.config;

import com.example.hotelManagement.exception.CannotPersistDataException;
import com.example.hotelManagement.exception.NotEnoughRoomException;
import com.example.hotelManagement.exception.RecordNotFoundException;
import com.example.hotelManagement.exception.UncoverableException;
import com.example.hotelManagement.util.ResponseUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionsHanlderConfig extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { RecordNotFoundException.class})
    protected ResponseEntity<Object> handleNotFoundData(RuntimeException ex, WebRequest request) {
        Map<String, String> bodyOfResponse = ResponseUtils.buildFailureResponse(ExceptionUtils.getMessage(ex));
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = { NotEnoughRoomException.class})
    protected ResponseEntity<Object> handleNotEnoughRoom(RuntimeException ex, WebRequest request) {
        Map<String, String> bodyOfResponse = ResponseUtils.buildFailureResponse(ExceptionUtils.getMessage(ex));
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = { UncoverableException.class, CannotPersistDataException.class})
    protected ResponseEntity<Object> handleInterServerError(RuntimeException ex, WebRequest request) {
        Map<String, String> bodyOfResponse = ResponseUtils.buildFailureResponse(ExceptionUtils.getMessage(ex));
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}
