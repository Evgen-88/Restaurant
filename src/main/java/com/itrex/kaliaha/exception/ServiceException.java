package com.itrex.kaliaha.exception;

import com.itrex.kaliaha.dto.DTO;

public class ServiceException extends Exception{
    private DTO dto;

    public ServiceException() {}

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public ServiceException(String message, DTO dto) {
        super(message);
        this.dto = dto;
    }

    public ServiceException(String message, Throwable throwable, DTO dto) {
        super(message, throwable);
        this.dto = dto;
    }

    public DTO getDto() {
        return dto;
    }
}