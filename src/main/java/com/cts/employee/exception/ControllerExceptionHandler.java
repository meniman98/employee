package com.cts.employee.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.server.ResponseStatusException;

import java.time.LocalTime;


@ControllerAdvice
public class ControllerExceptionHandler {
    public static ErrorObject errorObject = new ErrorObject();


    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorObject> notFoundHandler(ResponseStatusException exception) {
        errorObject.setReason(exception.getReason());
        errorObject.setStatus(exception.getStatusCode().value());
        errorObject.setTimeStamp(LocalTime.now());
        return new ResponseEntity<>(errorObject, exception.getStatusCode());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorObject> invalidArgument(MethodArgumentNotValidException exception) {
        errorObject.setReason("Invalid object has been passed in. Ensure the fields are valid"
                + System.lineSeparator()
                + exception.getMessage());
        errorObject.setStatus(exception.getStatusCode().value());
        errorObject.setTimeStamp(LocalTime.now());
        return new ResponseEntity<ErrorObject>(errorObject, exception.getStatusCode());
    }
}
