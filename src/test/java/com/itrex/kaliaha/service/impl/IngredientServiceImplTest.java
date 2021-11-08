package com.itrex.kaliaha.service.impl;

import com.itrex.kaliaha.dto.IngredientSaveDTO;
import com.itrex.kaliaha.entity.Ingredient;
import com.itrex.kaliaha.enums.Measurement;
import com.itrex.kaliaha.repository.BaseRepositoryTest;
import com.itrex.kaliaha.repository.IngredientRepository;
import com.itrex.kaliaha.service.IngredientService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IngredientServiceImplTest extends BaseRepositoryTest {
    @Autowired
    IngredientService ingredientService;
    @Autowired
    IngredientRepository ingredientRepository;

    @Test
    void findById() {
        //given
        IngredientSaveDTO expected = new IngredientSaveDTO(1L, "Мясо", 800, 1500, Measurement.KILOGRAM);

        //when
        IngredientSaveDTO actual = ingredientService.findById(1L);

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAll() {
        //given
        int expectedCount = 5;

        //when
        List<IngredientSaveDTO> actual = ingredientService.findAll();

        //then
        Assertions.assertEquals(expectedCount, actual.size());
    }

    @Test
    void add() {

    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}