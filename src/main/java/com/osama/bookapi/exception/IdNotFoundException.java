package com.osama.bookapi.exception;

// Custom exception class for handling "Id not found" cases
// Extends RuntimeException → unchecked exception (no need to handle everywhere)
public class IdNotFoundException extends RuntimeException {

    // Constructor to pass custom error message
    public IdNotFoundException(String message) {
        super(message);
    }
}