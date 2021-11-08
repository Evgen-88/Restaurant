package com.itrex.kaliaha.exception;

public class DishIsNotOrderedException extends Exception{
    public DishIsNotOrderedException() {
    }

    public DishIsNotOrderedException(String message) {
        super(message);
    }

    public DishIsNotOrderedException(String message, Throwable cause) {
        super(message, cause);
    }

    public DishIsNotOrderedException(Throwable cause) {
        super(cause);
    }
}
