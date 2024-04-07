package com.iraychev.server.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BookingDateOverlapException.class)
    public ResponseEntity<String> handleBookingOverlapException(BookingDateOverlapException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<String> handleGeneralException() {
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
//    }
}
