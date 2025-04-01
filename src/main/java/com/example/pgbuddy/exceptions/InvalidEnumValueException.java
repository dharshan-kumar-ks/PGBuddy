package com.example.pgbuddy.exceptions;

public class InvalidEnumValueException extends RuntimeException {
    public InvalidEnumValueException(String message) {
        super(message);
    }
}
