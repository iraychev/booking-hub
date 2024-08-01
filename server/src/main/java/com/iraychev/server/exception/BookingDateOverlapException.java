package com.iraychev.server.exception;

public class BookingDateOverlapException extends RuntimeException {

    public BookingDateOverlapException(String message) {
        super(message);
    }
}
