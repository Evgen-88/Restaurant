package com.itrex.kaliaha.repository.impl;

import com.itrex.kaliaha.config.ApplicationContextConfiguration;
import com.itrex.kaliaha.entity.Composition;
import com.itrex.kaliaha.entity.Dish;
import com.itrex.kaliaha.entity.Ingredient;
import com.itrex.kaliaha.repository.BaseRepositoryTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.ArrayList;
import java.util.List;

@SpringJUnitConfig
@ContextConfiguration(classes = ApplicationContextConfiguration.class)
public class CompositionRepositoryImplTest extends BaseRepositoryTest {
    @Autowired
    private CompositionRepositoryImpl compositionRepository;

    private final List<Composition> compositions;

    public CompositionRepositoryImplTest() {
        compositions = new ArrayList<>() {{
            add(new Composition(1L,new Dish(1L), new Ingredient(1L), 100));
            add(new Composition(2L,new Dish(1L), new Ingredient(2L), 450));
            add(new Composition(3L,new Dish(1L), new Ingredient(3L), 43));
            add(new Composition(4L,new Dish(2L), new Ingredient(1L), 132));
            add(new Composition(5L,new Dish(2L), new Ingredient(2L), 12));
            add(new Composition(6L,new Dish(3L), new Ingredient(4L), 100));
            add(new Composition(7L,new Dish(3L), new Ingredient(1L), 450));
            add(new Composition(8L,new Dish(3L), new Ingredient(5L), 43));
            add(new Composition(9L,new Dish(3L), new Ingredient(2L), 132));
        }};
    }

    @Test
    public void findByIdTest_validData_shouldReturnCompositionById() {
        //given
        Composition expected = compositions.get(0);

        //when
        Composition actual = compositionRepository.findById(expected.getId());

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void findAllTest_validData_shouldReturnAllExistingCompositions() {
        //given && when
        List<Composition> actual = compositionRepository.findAll();

        //then
        Assertions.assertEquals(compositions, actual);
    }

    @Test
    public void addTest_validData_shouldAddComposition() {
        //given
        List<Composition> expected = compositionRepository.findAll();

        Assertions.assertEquals(compositions.size(), expected.size());

        //when
        Composition newActual = new Composition(new Dish(2L), new Ingredient(4L), 100);
        boolean isAdded = compositionRepository.add(newActual);
        Composition newExpected = new Composition(10L, new Dish(2L), new Ingredient(4L), 100);
        expected.add(newExpected);

        //then
        Assertions.assertTrue(isAdded);
        Assertions.assertEquals(newExpected, newActual);
        Assertions.assertEquals(newExpected, compositionRepository.findById(newActual.getId()));
    }

    @Test
    public void updateTest_validData_shouldUpdateComposition() {
        //given
        Composition expected = new Composition(1L, new Dish(1L), new Ingredient(1L), 200);
        Composition actual = compositionRepository.findById(expected.getId());

        Assertions.assertEquals(expected.getId(), actual.getId());

        //when
        actual.setQuantity(200);
        boolean isUpdated = compositionRepository.update(actual);

        //then
        Assertions.assertTrue(isUpdated);
        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(expected, compositionRepository.findById(actual.getId()));
    }

    @Test
    public void deleteTest_validData_shouldDeleteComposition() {
        //given
        Composition expected = compositions.get(0);
        Composition actual = compositionRepository.findById(1L);

        Assertions.assertEquals(expected, actual);

        //when
        boolean isDeleted = compositionRepository.delete(actual.getId());

        //then
        Assertions.assertTrue(isDeleted);
        Assertions.assertNull(compositionRepository.findById(actual.getId()));
    }
}