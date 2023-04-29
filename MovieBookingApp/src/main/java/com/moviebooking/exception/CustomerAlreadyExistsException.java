package com.moviebooking.exception;

public class CustomerAlreadyExistsException extends RuntimeException {

    public CustomerAlreadyExistsException() {
        super();
    }

    public CustomerAlreadyExistsException(String message) {
        super(message);
    }
}
