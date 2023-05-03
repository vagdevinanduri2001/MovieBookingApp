package com.moviebooking.exception;

public class MovieAlreadyExistsException extends RuntimeException{
    public MovieAlreadyExistsException(){super();}
    public MovieAlreadyExistsException(String message){super(message);}
}
