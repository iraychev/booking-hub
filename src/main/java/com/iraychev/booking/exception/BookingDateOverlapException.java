package com.iraychev.booking.exception;

public class BookingDateOverlapException extends RuntimeException{

    public BookingDateOverlapException(String message){
        super(message);
    }
}
