package com.itrex.kaliaha.controller;

import com.itrex.kaliaha.exception.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class AbstractController {
    protected ResponseEntity<?> getResponseEntityForException(ServiceException ex) {
        return ex.getMessage() != null
                ? new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST)
                : new ResponseEntity<>("Nothing is found", HttpStatus.NOT_FOUND);
    }
}