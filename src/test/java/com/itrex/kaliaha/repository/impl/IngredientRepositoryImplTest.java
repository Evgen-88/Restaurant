package com.itrex.kaliaha.repository.impl;

import com.itrex.kaliaha.config.ApplicationContextConfiguration;
import com.itrex.kaliaha.entity.Composition;
import com.itrex.kaliaha.entity.Ingredient;
import com.itrex.kaliaha.enums.Measurement;
import com.itrex.kaliaha.repository.BaseRepositoryTest;
import com.itrex.kaliaha.repository.IngredientRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.ArrayList;
import java.util.List;

@SpringJUnitConfig
@ContextConfiguration(classes = ApplicationContextConfiguration.class)
public class IngredientRepositoryImplTest extends BaseRepositoryTest {
    @Autowired
    private IngredientRepository ingredientRepository;
    private final List<Ingredient> ingredients;

    public IngredientRepositoryImplTest() {
        ingredients = new ArrayList<>() {{
            add(new Ingredient(1L, "Мясо", 800, 1500, Measurement.KILOGRAM));
            add(new Ingredient(2L, "Картошка", 300, 777, Measurement.KILOGRAM));
            add(new Ingredient(3L, "Рис", 350, 1111, Measurement.KILOGRAM));
            add(new Ingredient(4L, "Чеснок", 15, 500, Measurement.GRAM));
            add(new Ingredient(5L, "Помидор", 13, 500, Measurement.GRAM));
        }};
    }

    @Test
    public void findByIdTest_validData_shouldReturnIngredientById() {
        //given
        Ingredient expected = ingredients.get(0);

        //when
        Ingredient actual = ingredientRepository.findById(expected.getId());

        //then
       Assertions.assertEquals(expected, actual);
    }

    @Test
    public void findAllTest_validData_shouldReturnAllExistingIngredients() {
        //given && when
        List<Ingredient> actual = ingredientRepository.findAll();

        //then
       Assertions.assertEquals(ingredients, actual);
    }

    @Test
    public void addTest_validData_shouldAddIngredient() {
        //given
        List<Ingredient> expected = ingredientRepository.findAll();

       Assertions.assertEquals(ingredients.size(), expected.size());

        //when
        Ingredient newActual = new Ingredient( "Петрушка", 8, 1500, Measurement.KILOGRAM);
        boolean isAdded = ingredientRepository.add(newActual);
        Ingredient newExpected = new Ingredient(6L, "Петрушка", 8, 1500, Measurement.KILOGRAM);
        expected.add(newExpected);

        //then
        Assertions.assertTrue(isAdded);
       Assertions.assertEquals(newExpected, newActual);
       Assertions.assertEquals(newExpected, ingredientRepository.findById(newActual.getId()));
    }

    @Test
    public void updateTest_validData_shouldUpdateIngredient() {
        //given
        Ingredient expected = new Ingredient(1L, "Рыба", 120, 1550, Measurement.GRAM);
        Ingredient actual = ingredientRepository.findById(expected.getId());

       Assertions.assertEquals(expected.getId(), actual.getId());

        //when
        actual.setIngredientName("Рыба");
        actual.setPrice(120);
        actual.setRemainder(1550);
        actual.setMeasurement(Measurement.GRAM);
        boolean isUpdated = ingredientRepository.update(actual);

        //then
        Assertions.assertTrue(isUpdated);
       Assertions.assertEquals(expected, actual);
       Assertions.assertEquals(expected, ingredientRepository.findById(actual.getId()));
    }

    @Test
    public void deleteTest_validData_shouldDeleteIngredient() {
        //given
        Ingredient expected = ingredients.get(0);
        Ingredient actual = ingredientRepository.findById(1L);

       Assertions.assertEquals(expected, actual);
       Assertions.assertEquals(3, ingredientRepository.findAllCompositionsThatIncludeIngredientById(actual.getId()).size());

        //when
        boolean isDeleted = ingredientRepository.delete(actual.getId());

        //then
        Assertions.assertTrue(isDeleted);
        Assertions.assertNull(ingredientRepository.findById(1L));
       Assertions.assertEquals(0, ingredientRepository.findAllCompositionsThatIncludeIngredientById(actual.getId()).size());
    }

    @Test
    public void findAllCompositionsThatIncludeIngredientByIdTest_validData_shouldFindCompositionsThanIncludeIngredient() {
        //given && when
        List<Composition> dishes = ingredientRepository.findAllCompositionsThatIncludeIngredientById(1L);

        //then
       Assertions.assertEquals(3, dishes.size());
    }
}