package com.itrex.kaliaha.service.impl;

import com.itrex.kaliaha.converters.DishConverter;
import com.itrex.kaliaha.dto.DishDTO;
import com.itrex.kaliaha.dto.DishListDTO;
import com.itrex.kaliaha.dto.DishSaveOrUpdateDTO;
import com.itrex.kaliaha.entity.Composition;
import com.itrex.kaliaha.entity.Dish;
import com.itrex.kaliaha.entity.Ingredient;
import com.itrex.kaliaha.enums.DishGroup;
import com.itrex.kaliaha.exception.ServiceException;
import com.itrex.kaliaha.repository.DishRepository;
import com.itrex.kaliaha.service.BaseServiceTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;

class DishServiceImplTest extends BaseServiceTest {
    @InjectMocks
    private DishServiceImpl dishService;
    @Mock
    private DishRepository dishRepository;

    public Dish getDishFindById() {
        List<Composition> compositions = new ArrayList<>(){{
            add(Composition.builder().id(1L).dish(Dish.builder().id(1L).build()).ingredient(getIngredients().get(0)).quantity(100).build());
            add(Composition.builder().id(2L).dish(Dish.builder().id(1L).build()).ingredient(getIngredients().get(1)).quantity(450).build());
            add(Composition.builder().id(3L).dish(Dish.builder().id(1L).build()).ingredient(getIngredients().get(1)).quantity(43).build());
        }};
        Dish dish = getDishes().get(0);
        dish.setCompositions(compositions);
        return dish;
    }

    public DishDTO getDishDTOExpected() {
        return DishConverter.toDTO(getDishFindById());
    }

    @Test
    void findByIdTest_shouldReturnDishDTO() {
        //given
        DishDTO expected = getDishDTOExpected();

        // when
        when(dishRepository.findById(1L)).thenReturn(getDishFindById());
        DishDTO actual = dishService.findById(1L);

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAllTest_shouldReturnAllDishListDTO() {
        //given
        List<DishListDTO> expected = getListDishListDTO();

        // when
        when(dishRepository.findAll()).thenReturn(getDishes());
        List<DishListDTO> actual = dishService.findAll();

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void addTest_shouldAddNewDish() {
        //given
        when(dishRepository.findAll()).thenReturn(getDishes());
        List<DishListDTO> actualList = dishService.findAll();

        Assertions.assertEquals(3, actualList.size());

        Dish dish = Dish.builder().dishName("Макароны по-немецки").price(2).dishGroup(DishGroup.HOT).dishDescription("Nice dish").imagePath("not image").build();
        List<Composition> compositions = new ArrayList<>(){{
            add(Composition.builder().dish(dish).ingredient(Ingredient.builder().id(1L).build()).quantity(100).build());
            add(Composition.builder().dish(dish).ingredient(Ingredient.builder().id(2L).build()).quantity(450).build());
            add(Composition.builder().dish(dish).ingredient(Ingredient.builder().id(3L).build()).quantity(43).build());
        }};
        dish.setCompositions(compositions);

        Map<Long, Integer> ingredients = new HashMap<>() {{put(1L, 100); put(2L, 450); put(3L, 43);}};

        // when
        DishSaveOrUpdateDTO expected = DishSaveOrUpdateDTO.builder().dishName("Макароны по-немецки").price(2).dishGroup(DishGroup.HOT).dishDescription("Nice dish").imagePath("not image").ingredients(ingredients).build();
        Dish beforeAdd = DishConverter.fromDTO(expected);
        Dish afterAdd =  DishConverter.fromDTO(expected);
        afterAdd.setId(4L);

        when(dishRepository.addWithCompositions(beforeAdd, compositions)).thenReturn(afterAdd);
        DishSaveOrUpdateDTO actual = dishService.add(expected);

        //then
        Assertions.assertNotNull(actual.getId());
        expected.setId(4L);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void updateTest_shouldUpdateDish() throws ServiceException {
        //given
        DishSaveOrUpdateDTO expected = DishSaveOrUpdateDTO.builder().id(1L).dishName("Шаньга").price(3).dishGroup(DishGroup.DRINK).dishDescription("Ужасно").imagePath("photo1111.img").build();
        Dish toUpdate = Dish.builder().id(1L).dishName("Шаньга").price(3).dishGroup(DishGroup.DRINK).dishDescription("Ужасно").imagePath("photo1111.img").build();

        //when
        when(dishRepository.update(toUpdate)).thenReturn(toUpdate);
        DishSaveOrUpdateDTO actual = dishService.update(expected);

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void deleteTest_shouldDeleteDish() {
        //given && when && then
        when(dishRepository.delete(1L)).thenReturn(true);
        Assertions.assertDoesNotThrow(() -> dishService.delete(1L));
    }
}