package com.moviebooking.exception;

public class MovieNotFoundException extends RuntimeException{
    public MovieNotFoundException(){super();}
    public MovieNotFoundException(String message){super(message);}
}
