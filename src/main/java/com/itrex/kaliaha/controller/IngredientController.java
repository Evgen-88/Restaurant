package com.itrex.kaliaha.controller;

import com.itrex.kaliaha.dto.IngredientDTO;
import com.itrex.kaliaha.exception.ServiceException;
import com.itrex.kaliaha.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
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
@RequestMapping("/ingredients")
@RequiredArgsConstructor
@Secured("admin")
public class IngredientController extends AbstractController {
    private final IngredientService ingredientService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getIngredientById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(ingredientService.findById(id), HttpStatus.OK);
        } catch (ServiceException ex) {
            return getResponseEntityForException(ex);
        }
    }

    @GetMapping
    public ResponseEntity<?> getIngredients() {
        try {
            return new ResponseEntity<>(ingredientService.findAll(), HttpStatus.OK);
        } catch (ServiceException ex) {
            return getResponseEntityForException(ex);
        }
    }

    @PostMapping
    public ResponseEntity<?> addIngredient(@RequestBody IngredientDTO ingredientDTO) {
        try {
            return new ResponseEntity<>(ingredientService.add(ingredientDTO), HttpStatus.OK);
        } catch (ServiceException ex) {
            return getResponseEntityForException(ex);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateIngredient(@RequestBody IngredientDTO ingredientDTO) {
        try {
            return new ResponseEntity<>(ingredientService.update(ingredientDTO), HttpStatus.OK);
        } catch (ServiceException ex) {
            return getResponseEntityForException(ex);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteIngredient(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(ingredientService.delete(id), HttpStatus.OK);
        } catch (ServiceException ex) {
            return getResponseEntityForException(ex);
        }
    }
}