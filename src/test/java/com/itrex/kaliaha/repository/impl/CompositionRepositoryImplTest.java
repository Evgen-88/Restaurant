package com.itrex.kaliaha.repository.impl;

import com.itrex.kaliaha.entity.Composition;
import com.itrex.kaliaha.entity.Dish;
import com.itrex.kaliaha.entity.Ingredient;
import com.itrex.kaliaha.repository.BaseRepository;
import com.itrex.kaliaha.repository.BaseRepositoryTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class CompositionRepositoryImplTest extends BaseRepositoryTest {
    private final List<Composition> compositions;
    @Autowired
    private BaseRepository<Composition> compositionRepository;

    public CompositionRepositoryImplTest() {
        compositions = new ArrayList<>() {{
            add(Composition.builder().id(1L).dish(Dish.builder().id(1L).build()).ingredient(Ingredient.builder().id(1L).build()).quantity(100).build());
            add(Composition.builder().id(2L).dish(Dish.builder().id(1L).build()).ingredient(Ingredient.builder().id(2L).build()).quantity(450).build());
            add(Composition.builder().id(3L).dish(Dish.builder().id(1L).build()).ingredient(Ingredient.builder().id(3L).build()).quantity(43).build());
            add(Composition.builder().id(4L).dish(Dish.builder().id(2L).build()).ingredient(Ingredient.builder().id(1L).build()).quantity(132).build());
            add(Composition.builder().id(5L).dish(Dish.builder().id(2L).build()).ingredient(Ingredient.builder().id(2L).build()).quantity(12).build());
            add(Composition.builder().id(6L).dish(Dish.builder().id(3L).build()).ingredient(Ingredient.builder().id(4L).build()).quantity(100).build());
            add(Composition.builder().id(7L).dish(Dish.builder().id(3L).build()).ingredient(Ingredient.builder().id(1L).build()).quantity(450).build());
            add(Composition.builder().id(8L).dish(Dish.builder().id(3L).build()).ingredient(Ingredient.builder().id(5L).build()).quantity(43).build());
            add(Composition.builder().id(9L).dish(Dish.builder().id(3L).build()).ingredient(Ingredient.builder().id(2L).build()).quantity(132).build());
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
        Composition newActual = Composition.builder().dish(Dish.builder().id(2L).build()).ingredient(Ingredient.builder().id(4L).build()).quantity(100).build();
        Composition addedComposition = compositionRepository.add(newActual);
        Composition newExpected = Composition.builder().id(10L).dish(Dish.builder().id(2L).build()).ingredient(Ingredient.builder().id(4L).build()).quantity(100).build();
        expected.add(newExpected);

        //then
        Assertions.assertNotNull(addedComposition.getId());
        Assertions.assertEquals(newExpected, addedComposition);
        Assertions.assertEquals(newExpected, compositionRepository.findById(addedComposition.getId()));
    }

    @Test
    public void updateTest_validData_shouldUpdateComposition() {
        //given
        Composition expected = Composition.builder().id(1L).dish(Dish.builder().id(1L).build()).ingredient(Ingredient.builder().id(1L).build()).quantity(200).build();
        Composition actual = compositionRepository.findById(expected.getId());

        Assertions.assertEquals(expected.getId(), actual.getId());

        //when
        actual.setQuantity(200);
        Composition updatedComposition = compositionRepository.update(actual);

        //then
        Assertions.assertEquals(expected, updatedComposition);
        Assertions.assertEquals(expected, compositionRepository.findById(updatedComposition.getId()));
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