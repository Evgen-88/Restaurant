package com.itrex.kaliaha.repository.impl;

import com.itrex.kaliaha.entity.Composition;
import com.itrex.kaliaha.entity.Ingredient;
import com.itrex.kaliaha.enums.Measurement;
import com.itrex.kaliaha.repository.BaseRepositoryTest;
import com.itrex.kaliaha.repository.IngredientRepository;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class IngredientRepositoryImplTest extends BaseRepositoryTest {
    private final IngredientRepository ingredientRepository;
    private final List<Ingredient> ingredients;

    public IngredientRepositoryImplTest() {
        ingredientRepository = getApplicationContext().getBean(IngredientRepositoryImpl.class);
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
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findAllTest_validData_shouldReturnAllExistingIngredients() {
        //given && when
        List<Ingredient> actual = ingredientRepository.findAll();

        //then
        Assert.assertEquals(ingredients, actual);
    }

    @Test
    public void addTest_validData_shouldAddIngredient() {
        //given
        List<Ingredient> expected = ingredientRepository.findAll();

        Assert.assertEquals(ingredients.size(), expected.size());

        //when
        Ingredient newActual = new Ingredient( "Петрушка", 8, 1500, Measurement.KILOGRAM);
        boolean isAdded = ingredientRepository.add(newActual);
        Ingredient newExpected = new Ingredient(6L, "Петрушка", 8, 1500, Measurement.KILOGRAM);
        expected.add(newExpected);

        //then
        Assert.assertTrue(isAdded);
        Assert.assertEquals(newExpected, newActual);
        Assert.assertEquals(newExpected, ingredientRepository.findById(newActual.getId()));
    }

    @Test
    public void updateTest_validData_shouldUpdateIngredient() {
        //given
        Ingredient expected = new Ingredient(1L, "Рыба", 120, 1550, Measurement.GRAM);
        Ingredient actual = ingredientRepository.findById(expected.getId());

        Assert.assertEquals(expected.getId(), actual.getId());

        //when
        actual.setIngredientName("Рыба");
        actual.setPrice(120);
        actual.setRemainder(1550);
        actual.setMeasurement(Measurement.GRAM);
        boolean isUpdated = ingredientRepository.update(actual);

        //then
        Assert.assertTrue(isUpdated);
        Assert.assertEquals(expected, actual);
        Assert.assertEquals(expected, ingredientRepository.findById(actual.getId()));
    }

    @Test
    public void deleteTest_validData_shouldDeleteIngredient() {
        //given
        Ingredient expected = ingredients.get(0);
        Ingredient actual = ingredientRepository.findById(1L);

        Assert.assertEquals(expected, actual);
        Assert.assertEquals(3, ingredientRepository.findAllCompositionsThatIncludeIngredientById(actual.getId()).size());

        //when
        boolean isDeleted = ingredientRepository.delete(actual.getId());

        //then
        Assert.assertTrue(isDeleted);
        Assert.assertNull(ingredientRepository.findById(1L));
        Assert.assertEquals(0, ingredientRepository.findAllCompositionsThatIncludeIngredientById(actual.getId()).size());
    }

    @Test
    public void findAllCompositionsThatIncludeIngredientByIdTest_validData_shouldFindCompositionsThanIncludeIngredient() {
        //given && when
        List<Composition> dishes = ingredientRepository.findAllCompositionsThatIncludeIngredientById(1L);

        //then
        Assert.assertEquals(3, dishes.size());
    }
}