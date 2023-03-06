package com.inai.courseproject.exceptions.handler;

import com.inai.courseproject.dto.ErrorStatusResponse;
import com.inai.courseproject.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(UsernameNotUniqueException.class)
    public ResponseEntity<ErrorStatusResponse> handle(UsernameNotUniqueException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorStatusResponse(e.getMessage()));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorStatusResponse> handle(BadCredentialsException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorStatusResponse(e.getMessage()));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorStatusResponse> handle(UsernameNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorStatusResponse(e.getMessage()));
    }

    @ExceptionHandler(InvalidDayException.class)
    public ResponseEntity<ErrorStatusResponse> handle(InvalidDayException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorStatusResponse(e.getMessage()));
    }

    @ExceptionHandler(OvercrowdedClassException.class)
    public ResponseEntity<ErrorStatusResponse> handle(OvercrowdedClassException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorStatusResponse(e.getMessage()));
    }

    @ExceptionHandler(LargeTimeSpanException.class)
    public ResponseEntity<ErrorStatusResponse> handle(LargeTimeSpanException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorStatusResponse(e.getMessage()));
    }

    @ExceptionHandler(ExistingFitnessClassException.class)
    public ResponseEntity<ErrorStatusResponse> handle(ExistingFitnessClassException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorStatusResponse(e.getMessage()));
    }

}
