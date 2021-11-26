package com.itrex.kaliaha.controller;

import com.itrex.kaliaha.dto.DishSaveOrUpdateDTO;
import com.itrex.kaliaha.exception.ServiceException;
import com.itrex.kaliaha.service.DishService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/dishes")
@RequiredArgsConstructor
public class DishController extends AbstractController{
    private final DishService dishService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getDishById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(dishService.findById(id), HttpStatus.OK);
        } catch (ServiceException ex) {
            return getResponseEntityForException(ex);
        }
    }

    @GetMapping
    public ResponseEntity<?> getDishes() {
        try {
            return new ResponseEntity<>(dishService.findAll(), HttpStatus.OK);
        } catch (ServiceException ex) {
            return getResponseEntityForException(ex);
        }
    }

    @PostMapping
    public ResponseEntity<?> addDish(@RequestBody DishSaveOrUpdateDTO dishSaveOrUpdateDTO) {
        try {
            return new ResponseEntity<>(dishService.add(dishSaveOrUpdateDTO), HttpStatus.OK);
        } catch (ServiceException ex) {
            return getResponseEntityForException(ex);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateDish(@RequestBody DishSaveOrUpdateDTO dishSaveOrUpdateDTO) {
        try {
            return new ResponseEntity<>(dishService.update(dishSaveOrUpdateDTO), HttpStatus.OK);
        } catch (ServiceException ex) {
            return getResponseEntityForException(ex);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDish(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(dishService.delete(id), HttpStatus.OK);
        } catch (ServiceException ex) {
            return getResponseEntityForException(ex);
        }
    }
}