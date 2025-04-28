package com.example.pgbuddy.exceptions;

// This class represents a custom exception that is thrown when a requested resource is not found in the DB tables
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
