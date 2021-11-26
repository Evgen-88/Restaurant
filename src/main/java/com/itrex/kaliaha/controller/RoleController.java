package com.itrex.kaliaha.controller;

import com.itrex.kaliaha.exception.ServiceException;
import com.itrex.kaliaha.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController extends AbstractController{
    private final RoleService roleService;

    @GetMapping
    public ResponseEntity<?> getRoles() {
        try {
            return new ResponseEntity<>(roleService.findAll(), HttpStatus.OK);
        } catch (ServiceException ex) {
            return getResponseEntityForException(ex);
        }
    }
}