package com.itrex.kaliaha.repository.impl;

import com.itrex.kaliaha.entity.Composition;
import com.itrex.kaliaha.entity.Dish;
import com.itrex.kaliaha.entity.Order;
import com.itrex.kaliaha.enums.DishGroup;
import com.itrex.kaliaha.repository.BaseRepositoryTest;
import com.itrex.kaliaha.repository.DishRepository;
import com.itrex.kaliaha.repository.OrderRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class DishRepositoryImplTest extends BaseRepositoryTest {
    private final List<Dish> dishes;
    @Autowired
    private DishRepository dishRepository;
    @Autowired
    private OrderRepository orderRepository;

    public DishRepositoryImplTest() {
        dishes = new ArrayList<>() {{
            add(Dish.builder().id(1L).dishName("Картошка с грибами").price(2).dishGroup(DishGroup.HOT).dishDescription("Очень вкусно").imagePath("photo.img").build());
            add(Dish.builder().id(2L).dishName("Салат по-французски").price(7).dishGroup(DishGroup.SALAD).dishDescription("Невкусно").imagePath("photo1.img").build());
            add(Dish.builder().id(3L).dishName("Макароны по-европейски").price(11).dishGroup(DishGroup.DRINK).dishDescription("Невероятно").imagePath("photo2.img").build());
        }};
    }

    @Test
    public void findByIdTest_validData_shouldReturnDishById() {
        //given
        Dish expected = dishes.get(0);

        //when
        Dish actual = dishRepository.findById(expected.getId());

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void findAllTest_validData_shouldReturnAllExistingDishes() {
        //given && //when
        List<Dish> actual = dishRepository.findAll();

        //then
        Assertions.assertEquals(dishes, actual);
    }

    @Test
    public void addTest_validData_shouldAddDish() {
        //given
        List<Dish> expected = dishRepository.findAll();

        Assertions.assertEquals(dishes.size(), expected.size());

        //when
        Dish newActual = Dish.builder().dishName("Макароны по-немецки").price(2).dishGroup(DishGroup.HOT).dishDescription("Nice dish").imagePath("not image").build();
        Dish addedDish = dishRepository.add(newActual);
        Dish newExpected = Dish.builder().id(4L).dishName("Макароны по-немецки").price(2).dishGroup(DishGroup.HOT).dishDescription("Nice dish").imagePath("not image").build();
        expected.add(newExpected);

        //then
        Assertions.assertNotNull(addedDish.getId());
        Assertions.assertEquals(newExpected, addedDish);
        Assertions.assertEquals(newExpected, dishRepository.findById(addedDish.getId()));
    }

    @Test
    public void updateTest_validData_shouldUpdateDish() {
        //given
        Dish expected = Dish.builder().id(1L).dishName("Шаньга").price(3).dishGroup(DishGroup.DRINK).dishDescription("Ужасно").imagePath("photo1111.img").build();
        Dish actual = dishRepository.findById(expected.getId());

        Assertions.assertEquals(expected.getId(), actual.getId());

        //when
        actual = Dish.builder().id(expected.getId()).dishName("Шаньга").price(3).dishGroup(DishGroup.DRINK).dishDescription("Ужасно").imagePath("photo1111.img").build();
        Dish updatedDish = dishRepository.update(actual);

        //then
        Assertions.assertEquals(expected, updatedDish);
        Assertions.assertEquals(expected, dishRepository.findById(updatedDish.getId()));
    }

    @Test
    public void deleteTest_validData_shouldDeleteDish() {
        //given
        Dish expected = dishes.get(0);
        Dish actual = dishRepository.findById(1L);

        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(4, dishRepository.findAllOrdersThatIncludeDishByDishId(actual.getId()).size());
        Assertions.assertEquals(3, dishRepository.getCompositionsByDishId(actual.getId()).size());

        //when
        boolean isDeleted = dishRepository.delete(actual.getId());

        //then
        Assertions.assertTrue(isDeleted);
        Assertions.assertNull(dishRepository.findById(1L));
        Assertions.assertEquals(0, dishRepository.findAllOrdersThatIncludeDishByDishId(actual.getId()).size());
        Assertions.assertEquals(0, dishRepository.getCompositionsByDishId(actual.getId()).size());
    }

    @Test
    public void findAllDishesInOrderByIdTest_validData_shouldFindAllDishesInOrder() {
        //given
        Order order = orderRepository.findById(1L);

        //when
        List<Dish> orderedDishes = dishRepository.findAllDishesInOrderById(order.getId());

        //then
        Assertions.assertEquals(3, orderedDishes.size());
    }

    @Test
    public void getCompositionsByDishIdTest_validData_shouldFindDishCompositions() {
        //given && when
        List<Composition> dishCompositions = dishRepository.getCompositionsByDishId(1L);

        //then
        Assertions.assertEquals(3, dishCompositions.size());
    }

    @Test
    public void findAllOrdersThatIncludeDishByDishIdTest_validData_shouldFindOrdersThatIncludeDish() {
        //given && when
        List<Order> orders = dishRepository.findAllOrdersThatIncludeDishByDishId(1L);

        //then
        Assertions.assertEquals(4, orders.size());
    }
}