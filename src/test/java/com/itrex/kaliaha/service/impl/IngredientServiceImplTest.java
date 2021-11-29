package com.itrex.kaliaha.service.impl;

import com.itrex.kaliaha.dto.IngredientDTO;
import com.itrex.kaliaha.entity.Ingredient;
import com.itrex.kaliaha.enums.Measurement;
import com.itrex.kaliaha.exception.ServiceException;
import com.itrex.kaliaha.repository.CompositionRepository;
import com.itrex.kaliaha.repository.IngredientRepository;
import com.itrex.kaliaha.service.BaseServiceTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

class IngredientServiceImplTest extends BaseServiceTest {
    @InjectMocks
    private IngredientServiceImpl ingredientService;
    @Mock
    private IngredientRepository ingredientRepository;
    @Mock
    private CompositionRepository compositionRepository;

    @Test
    void findByIdTest_shouldReturnIngredientDTO() throws ServiceException {
        //given
        IngredientDTO expected = getIngredientsDTO().get(0);

        // when
        when(ingredientRepository.findById(1L)).thenReturn(Optional.of(getIngredients().get(0)));
        IngredientDTO actual = ingredientService.findById(1L);

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAllTest_shouldReturnAllIngredientDTO() throws ServiceException {
        //given
        List<IngredientDTO> expected = getIngredientsDTO();

        // when
        when(ingredientRepository.findAll()).thenReturn(getIngredients());
        List<IngredientDTO> actual = ingredientService.findAll();

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void addTest_shouldAddNewIngredient() throws ServiceException {
        //given
        when(ingredientRepository.findAll()).thenReturn(getIngredients());
        List<IngredientDTO> actualList = ingredientService.findAll();

        Assertions.assertEquals(5, actualList.size());

        // when
        IngredientDTO expected = IngredientDTO.builder().ingredientName("Петрушка").price(8).remainder(1500).measurement(Measurement.KILOGRAM).build();
        Ingredient beforeAdd = Ingredient.builder().ingredientName("Петрушка").price(8).remainder(1500).measurement(Measurement.KILOGRAM).build();
        Ingredient afterAdd = Ingredient.builder().id(6L).ingredientName("Петрушка").price(8).remainder(1500).measurement(Measurement.KILOGRAM).build();

        when(ingredientRepository.save(beforeAdd)).thenReturn(afterAdd);
        IngredientDTO actual = ingredientService.add(expected);

        //then
        Assertions.assertNotNull(actual.getId());
        expected.setId(6L);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void updateTest_shouldUpdateIngredient() throws ServiceException {
        //given
        IngredientDTO expected = IngredientDTO.builder().id(1L).ingredientName("Рыба").price(120).remainder(1550).measurement(Measurement.GRAM).build();

        //when
        when(ingredientRepository.findById(1L)).thenReturn(Optional.of(getIngredients().get(0)));
        IngredientDTO actual = ingredientService.update(expected);

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void deleteTest_shouldDeleteDish() {
        //given
        Ingredient ingredient = getIngredients().get(0);
        ingredient.setCompositions(new ArrayList<>());

        //when
        when(ingredientRepository.findById(1L)).thenReturn(Optional.of(ingredient));

        //then
        Assertions.assertDoesNotThrow(() -> ingredientService.delete(1L));
    }
}