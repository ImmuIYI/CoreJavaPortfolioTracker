package com.tracker.exceptions;

public class UsernameTakenException extends Exception {

    public UsernameTakenException(String message) {
        super(message);
    }
}