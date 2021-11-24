package com.itrex.kaliaha.controller;

import com.itrex.kaliaha.dto.DishDTO;
import com.itrex.kaliaha.dto.DishListDTO;
import com.itrex.kaliaha.dto.DishSaveOrUpdateDTO;
import com.itrex.kaliaha.exception.InvalidIdParameterServiceException;
import com.itrex.kaliaha.exception.ServiceException;
import com.itrex.kaliaha.service.DishService;

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
@RequestMapping("/dishes")
@RequiredArgsConstructor
public class DishController {
    private final DishService dishService;

    @GetMapping("/{id}")
    public DishDTO getDishById(@PathVariable Long id) {
        return dishService.findById(id);
    }

    @GetMapping
    public List<DishListDTO> getDishes() {
        return dishService.findAll();
    }

    @PostMapping(params = {"dishName", "price", "dishGroup", "dishDescription", "imagePath", "ingredients"})
    public DishSaveOrUpdateDTO addDish(DishSaveOrUpdateDTO dishSaveOrUpdateDTO) {
        return dishService.add(dishSaveOrUpdateDTO);
    }

    @PutMapping(params = {"id", "dishName", "price", "dishGroup", "dishDescription", "imagePath", "ingredients"})
    public DishSaveOrUpdateDTO updateDish(DishSaveOrUpdateDTO dishSaveOrUpdateDTO) throws ServiceException {
        return dishService.update(dishSaveOrUpdateDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteDish(@PathVariable Long id) throws InvalidIdParameterServiceException {
        dishService.delete(id);
    }
}