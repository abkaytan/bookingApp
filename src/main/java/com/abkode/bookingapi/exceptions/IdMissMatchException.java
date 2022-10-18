package com.abkode.bookingapi.exceptions;

public class IdMissMatchException extends RuntimeException{
    public IdMissMatchException(String message) {
        super(message);
    }
}
