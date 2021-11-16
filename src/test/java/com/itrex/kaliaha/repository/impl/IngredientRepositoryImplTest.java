package com.itrex.kaliaha.repository.impl;

import com.itrex.kaliaha.entity.Composition;
import com.itrex.kaliaha.entity.Ingredient;
import com.itrex.kaliaha.enums.Measurement;
import com.itrex.kaliaha.repository.BaseRepositoryTest;
import com.itrex.kaliaha.repository.IngredientRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class IngredientRepositoryImplTest extends BaseRepositoryTest {
    private final List<Ingredient> ingredients;
    @Autowired
    private IngredientRepository ingredientRepository;

    public IngredientRepositoryImplTest() {
        ingredients = new ArrayList<>() {{
            add(Ingredient.builder().id(1L).ingredientName("Мясо").price(800).remainder(1500).measurement(Measurement.KILOGRAM).build());
            add(Ingredient.builder().id(2L).ingredientName("Картошка").price(300).remainder(777).measurement(Measurement.KILOGRAM).build());
            add(Ingredient.builder().id(3L).ingredientName("Рис").price(350).remainder(1111).measurement(Measurement.KILOGRAM).build());
            add(Ingredient.builder().id(4L).ingredientName("Чеснок").price(15).remainder(500).measurement(Measurement.GRAM).build());
            add(Ingredient.builder().id(5L).ingredientName("Помидор").price(13).remainder(500).measurement(Measurement.GRAM).build());
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

        Ingredient newActual = Ingredient.builder().ingredientName("Петрушка").price(8).remainder(1500).measurement(Measurement.KILOGRAM).build();
        Ingredient addedIngredient = ingredientRepository.add(newActual);
        Ingredient newExpected = Ingredient.builder().id(6L).ingredientName("Петрушка").price(8).remainder(1500).measurement(Measurement.KILOGRAM).build();
        expected.add(newExpected);

        //then
        Assertions.assertNotNull(addedIngredient.getId());
        Assertions.assertEquals(newExpected, addedIngredient);
        Assertions.assertEquals(newExpected, ingredientRepository.findById(addedIngredient.getId()));
    }

    @Test
    public void updateTest_validData_shouldUpdateIngredient() {
        //given
        Ingredient expected = Ingredient.builder().id(1L).ingredientName("Рыба").price(120).remainder(1550).measurement(Measurement.GRAM).build();
        Ingredient actual = ingredientRepository.findById(expected.getId());

        Assertions.assertEquals(expected.getId(), actual.getId());

        //when
        actual = Ingredient.builder().id(expected.getId()).ingredientName("Рыба").price(120).remainder(1550).measurement(Measurement.GRAM).build();

        Ingredient updatedIngredient = ingredientRepository.update(actual);

        //then
        Assertions.assertEquals(expected, updatedIngredient);
        Assertions.assertEquals(expected, ingredientRepository.findById(updatedIngredient.getId()));
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

    @Test
    public void getIngredientByCompositionIdTest_validData_shouldFindDishThatContainsComposition() {
        //given
        Ingredient expected = Ingredient.builder().id(1L).ingredientName("Мясо").price(800).remainder(1500).measurement(Measurement.KILOGRAM).build();

        // when
        Ingredient actual = ingredientRepository.getIngredientByCompositionId(1L);

        //then
        Assertions.assertEquals(expected, actual);
    }
}