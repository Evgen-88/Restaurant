package com.itrex.kaliaha.exception;

public class InvalidIdParameterServiceException extends Exception{
    private Long id;
    public InvalidIdParameterServiceException() {
    }

    public InvalidIdParameterServiceException(String message) {
        super(message);
    }

    public InvalidIdParameterServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidIdParameterServiceException(Throwable cause) {
        super(cause);
    }

    public InvalidIdParameterServiceException(String message, Long id) {
        super(message);
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}