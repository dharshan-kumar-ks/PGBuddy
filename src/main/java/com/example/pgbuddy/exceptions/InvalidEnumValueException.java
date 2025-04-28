package com.example.pgbuddy.exceptions;

// This class represents a custom exception that is thrown when an invalid enum value is encountered.
public class InvalidEnumValueException extends RuntimeException {
    public InvalidEnumValueException(String message) {
        super(message);
    }
}
