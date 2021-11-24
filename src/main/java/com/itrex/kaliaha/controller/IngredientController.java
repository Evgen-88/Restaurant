package com.itrex.kaliaha.controller;

import com.itrex.kaliaha.dto.IngredientDTO;
import com.itrex.kaliaha.exception.InvalidIdParameterServiceException;
import com.itrex.kaliaha.exception.ServiceException;
import com.itrex.kaliaha.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

@RestController
@RequestMapping("/ingredients")
@RequiredArgsConstructor
public class IngredientController {
    private final IngredientService ingredientService;

    @GetMapping("/{id}")
    public IngredientDTO getIngredientById(@PathVariable Long id) {
        return ingredientService.findById(id);
    }

    @GetMapping
    public List<IngredientDTO> getIngredients() {
        return ingredientService.findAll();
    }

    @PostMapping(params = {"ingredientName", "price", "remainder", "measurement"})
    public IngredientDTO addIngredient(IngredientDTO ingredientDTO) throws ServiceException {
        return ingredientService.add(ingredientDTO);
    }

    @PutMapping(params = {"id", "ingredientName", "price", "remainder", "measurement"})
    public IngredientDTO updateIngredient(IngredientDTO ingredientDTO) throws ServiceException {
        return ingredientService.update(ingredientDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteIngredient(@PathVariable Long id) throws InvalidIdParameterServiceException {
        ingredientService.delete(id);
    }
}