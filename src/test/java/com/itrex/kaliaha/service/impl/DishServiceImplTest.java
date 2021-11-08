package com.itrex.kaliaha.service.impl;

import com.itrex.kaliaha.dto.DishDTO;
import com.itrex.kaliaha.dto.DishIngredientDTO;
import com.itrex.kaliaha.dto.DishListDTO;
import com.itrex.kaliaha.dto.DishSaveDTO;
import com.itrex.kaliaha.entity.Dish;
import com.itrex.kaliaha.enums.DishGroup;
import com.itrex.kaliaha.enums.Measurement;
import com.itrex.kaliaha.exception.InvalidIdParameterServiceException;
import com.itrex.kaliaha.exception.ServiceException;
import com.itrex.kaliaha.repository.BaseRepositoryTest;
import com.itrex.kaliaha.repository.DishRepository;
import com.itrex.kaliaha.repository.IngredientRepository;
import com.itrex.kaliaha.service.DishService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class DishServiceImplTest extends BaseRepositoryTest {
    @Autowired
    private DishService dishService;
    @Autowired
    private DishRepository dishRepository;
    @Autowired
    IngredientRepository ingredientRepository;

    @Test
    void findByIdTest_shouldReturnDishDTOWithIncludedIngredients() {
        //given
        List<DishIngredientDTO> ingredients = new ArrayList<>(){{
            add(new DishIngredientDTO(1L, 1L, "Мясо", Measurement.KILOGRAM, 100, 80));
            add(new DishIngredientDTO(2L, 2L, "Картошка", Measurement.KILOGRAM, 450, 135));
            add(new DishIngredientDTO(3L, 3L, "Рис", Measurement.KILOGRAM, 43, 15));
        }};
        DishDTO expected = new DishDTO(1L, "Картошка с грибами", 2, DishGroup.HOT, "Очень вкусно", "photo.img", ingredients);

        //when
        DishDTO actual = dishService.findById(1L);

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAll() {
        //given
        List<DishListDTO> expected = new ArrayList<>() {{
            add(new DishListDTO(1L,"Картошка с грибами", 2));
            add(new DishListDTO(2L, "Салат по-французски", 7));
            add(new DishListDTO(3L,"Макароны по-европейски", 11));
        }};

        //when
        List<DishListDTO> actual = dishService.findAll();

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void add() {
        //given
        Map<Long, Integer> ingredients = new HashMap<>(){{
            put(1L, 100);
            put(2L, 540);
        }};
        DishSaveDTO expected = new DishSaveDTO();
        expected.setDishGroup(DishGroup.HOT);
        expected.setPrice(5400);
        expected.setDishName("фирменное блюдо");
        expected.setImagePath("asd");
        expected.setDishDescription("delicious");
        expected.setIngredients(ingredients);

        //when
        dishService.add(expected);

        //then
        Assertions.assertEquals(4, expected.getId());
        Assertions.assertEquals(2, dishRepository.getCompositionsByDishId(expected.getId()).size());
    }

    @Test
    void update() throws ServiceException {
        //given
        DishSaveDTO expected = new DishSaveDTO();
        expected.setId(1L);
        expected.setDishGroup(DishGroup.SALAD);
        expected.setPrice(5400);
        expected.setDishName("фирменное блюдо");
        expected.setImagePath("asd1");
        expected.setDishDescription("delicious1");
        Dish dish = dishRepository.findById(1L);

        Assertions.assertEquals(new Dish(1L, "Картошка с грибами", 2, DishGroup.HOT, "Очень вкусно", "photo.img"), dish);

        //when
        dishService.update(expected);

        //then
        dish = dishRepository.findById(1L);
        Assertions.assertEquals(new Dish(1L, "фирменное блюдо", 5400, DishGroup.SALAD, "delicious1", "asd1"), dish);
    }

    @Test
    void delete() throws InvalidIdParameterServiceException {
        //given
        Dish dish = dishRepository.findById(1L);

        //when
        dishService.delete(dish.getId());

        //then
        Assertions.assertNull(dishRepository.findById(1L));
    }
}