package com.itrex.kaliaha.exception;

public class DishIsNotOrderedException extends Exception {
    private  Long dishId;

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

    public DishIsNotOrderedException(String message, Long dishId) {
        super(message);
        this.dishId = dishId;
    }

    public Long getDishId() {
        return dishId;
    }
}