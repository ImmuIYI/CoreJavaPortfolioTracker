package com.tracker.exceptions;

// By extending Exception, we make this a "checked exception"
public class InvalidLoginException extends Exception {
    
    public InvalidLoginException(String message) {
        super(message);
    }
}