package com.cts.employee.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.server.ResponseStatusException;

import java.time.LocalTime;

import static java.time.temporal.ChronoUnit.SECONDS;


@ControllerAdvice
public class ControllerExceptionHandler {

    public static final LocalTime TIME_NOW = LocalTime.now().truncatedTo(SECONDS);
    public static ErrorObject errorObject = new ErrorObject();


    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorObject> notFoundHandler(ResponseStatusException exception) {
        errorObject.setReason(exception.getReason());
        errorObject.setStatus(exception.getStatusCode().value());
        errorObject.setTimeStamp(TIME_NOW);
        return new ResponseEntity<>(errorObject, exception.getStatusCode());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorObject> invalidArgument(MethodArgumentNotValidException exception) {
        errorObject.setReason("Invalid object has been passed in. Ensure the fields are valid"
                + System.lineSeparator()
                + exception.getAllErrors());
        errorObject.setStatus(exception.getStatusCode().value());
        errorObject.setTimeStamp(TIME_NOW);
        return new ResponseEntity<ErrorObject>(errorObject, exception.getStatusCode());
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorObject> genericExceptionOrError(Throwable throwable) {
        errorObject.setReason(throwable.getMessage());
        errorObject.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorObject.setTimeStamp(TIME_NOW);
        return new ResponseEntity<ErrorObject>(errorObject, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
