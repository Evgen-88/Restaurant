package com.itrex.kaliaha.repository.impl;

import com.itrex.kaliaha.entity.Ingredient;
import com.itrex.kaliaha.entity.util.Measurement;
import com.itrex.kaliaha.repository.BaseRepositoryTest;
import com.itrex.kaliaha.repository.IngredientRepository;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class JDBCIngredientRepositoryImplTest extends BaseRepositoryTest {
    private final IngredientRepository ingredientRepository;
    private final List<Ingredient> ingredients;

    public JDBCIngredientRepositoryImplTest() {
        super();
        ingredientRepository = new JDBCIngredientRepositoryImpl(getConnectionPool());
        ingredients = new ArrayList<>() {{
            add(new Ingredient(1L, "Мясо", 800, 1500, Measurement.Килограмм));
            add(new Ingredient(2L, "Картошка", 300, 777, Measurement.Килограмм));
            add(new Ingredient(3L, "Рис", 350, 1111, Measurement.Килограмм));
            add(new Ingredient(4L, "Чеснок", 15, 500, Measurement.Грамм));
            add(new Ingredient(5L, "Помидор", 13, 500, Measurement.Грамм));
        }};
    }

    @Test
    public void selectById_validData_shouldReturnIngredientById() {
        //given
        Ingredient expected = ingredients.get(0);
        //when
        Ingredient actual = ingredientRepository.selectById(expected.getId());
        //then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void selectAll_validData_shouldReturnAllIngredients() {
        //given && when
        List<Ingredient> actual = ingredientRepository.selectAll();
        //then
        Assert.assertEquals(ingredients, actual);
    }

    @Test
    public void add_validData_shouldAddIngredient() {
        //given
        Ingredient expected = new Ingredient(6L, "Петрушка", 8, 1500, Measurement.Килограмм);
        //when
        Ingredient actual = new Ingredient("Петрушка", 8, 1500, Measurement.Килограмм);
        ingredientRepository.add(actual);
        //then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void addAll_validData_shouldAddListIngredients() {
        //given
        List<Ingredient> expected = new ArrayList<>() {{
            add(new Ingredient(6L, "Петрушка", 8, 1500, Measurement.Килограмм));
            add(new Ingredient(7L, "Баклажан", 3, 7777, Measurement.Килограмм));

        }};
        //when
        List<Ingredient> actual = new ArrayList<>() {{
            add(new Ingredient("Петрушка", 8, 1500, Measurement.Килограмм));
            add(new Ingredient("Баклажан", 3, 7777, Measurement.Килограмм));
        }};
        ingredientRepository.addAll(actual);
        //then
        assertEquals(expected, actual);
    }

    @Test
    public void update_validData_shouldUpdateIngredient() {
        //given
        Ingredient expected = new Ingredient(1L, "Рыба", 120, 1550, Measurement.Килограмм);
        Ingredient actual = ingredients.get(0);
        //when
        actual.setIngredientName("Рыба");
        actual.setPrice(120);
        actual.setRemainder(1550);
        actual.setMeasurement(Measurement.Килограмм);
        //then
        Assert.assertTrue(ingredientRepository.update(actual));
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void remove_validData_shouldRemoveIngredient() {
        //given
        Ingredient checkAddition = new Ingredient(6L, "Шоколад", 8, 1500, Measurement.Килограмм);
        Ingredient newIngredient = new Ingredient(-1L, "Шоколад", 8, 1500, Measurement.Килограмм);
        ingredientRepository.add(newIngredient);
        Assert.assertEquals(checkAddition, newIngredient);
        //when
        boolean actual = ingredientRepository.remove(newIngredient.getId());
        //then
        Assert.assertTrue(actual);
    }
}