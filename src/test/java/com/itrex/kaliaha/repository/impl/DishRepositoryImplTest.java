package com.itrex.kaliaha.repository.impl;

import com.itrex.kaliaha.entity.Composition;
import com.itrex.kaliaha.entity.Dish;
import com.itrex.kaliaha.entity.Order;
import com.itrex.kaliaha.enums.DishGroup;
import com.itrex.kaliaha.repository.BaseRepositoryTest;
import com.itrex.kaliaha.repository.DishRepository;
import com.itrex.kaliaha.repository.OrderRepository;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class DishRepositoryImplTest extends BaseRepositoryTest {
    private final DishRepository dishRepository;
    private final List<Dish> dishes;

    public DishRepositoryImplTest() {
        dishRepository = new DishRepositoryImpl();
        dishes = new ArrayList<>() {{
            add(new Dish(1L, "Картошка с грибами", 2, DishGroup.HOT, "Очень вкусно", "photo.img"));
            add(new Dish(2L, "Салат по-французски", 7, DishGroup.SALAD, "Невкусно", "photo1.img"));
            add(new Dish(3L, "Макароны по-европейски", 11, DishGroup.DRINK, "Невероятно", "photo2.img"));
        }};
    }

    @Test
    public void findByIdTest_validData_shouldReturnDishById() {
        //given
        Dish expected = dishes.get(0);

        //when
        Dish actual = dishRepository.findById(expected.getId());

        //then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findAllTest_validData_shouldReturnAllExistingDishes() {
        //given && //when
        List<Dish> actual = dishRepository.findAll();

        //then
        Assert.assertEquals(dishes, actual);
    }

    @Test
    public void addTest_validData_shouldAddDish() {
        //given
        List<Dish> expected = dishRepository.findAll();

        Assert.assertEquals(dishes.size(), expected.size());

        //when
        Dish newActual = new Dish("Макароны по-немецки", 2, DishGroup.HOT, "Nice dish", "not image");
        boolean isAdded = dishRepository.add(newActual);
        Dish newExpected = new Dish(4L, "Макароны по-немецки", 2, DishGroup.HOT, "Nice dish", "not image");
        expected.add(newExpected);

        //then
        Assert.assertTrue(isAdded);
        Assert.assertEquals(newExpected, newActual);
        Assert.assertEquals(newExpected, dishRepository.findById(newActual.getId()));
    }

    @Test
    public void updateTest_validData_shouldUpdateDish() {
        //given
        Dish expected = new Dish(1L, "Шаньга", 3, DishGroup.DRINK, "Ужасно", "photo1111.img");
        Dish actual = dishRepository.findById(expected.getId());

        Assert.assertEquals(expected.getId(), actual.getId());

        //when
        actual.setDishName("Шаньга");
        actual.setPrice(3);
        actual.setDishGroup(DishGroup.DRINK);
        actual.setDishDescription("Ужасно");
        actual.setImagePath("photo1111.img");
        boolean isUpdated = dishRepository.update(actual);

        //then
        Assert.assertTrue(isUpdated);
        Assert.assertEquals(expected, actual);
        Assert.assertEquals(expected, dishRepository.findById(actual.getId()));
    }

    @Test
    public void deleteTest_validData_shouldDeleteDish() {
        //given
        Dish expected = dishes.get(0);
        Dish actual = dishRepository.findById(1L);

        Assert.assertEquals(expected, actual);
        Assert.assertEquals(4, dishRepository.findAllOrdersThatIncludeDishByDishId(actual.getId()).size());
        Assert.assertEquals(3, dishRepository.getCompositionsByDishId(actual.getId()).size());

        //when
        boolean isDeleted = dishRepository.delete(actual.getId());

        //then
        Assert.assertTrue(isDeleted);
        Assert.assertNull(dishRepository.findById(1L));
        Assert.assertEquals(0, dishRepository.findAllOrdersThatIncludeDishByDishId(actual.getId()).size());
        Assert.assertEquals(0, dishRepository.getCompositionsByDishId(actual.getId()).size());
    }

    @Test
    public void findAllDishesInOrderByIdTest_validData_shouldFindAllDishesInOrder() {
        OrderRepository orderRepository = new OrderRepositoryImpl();

        //given
        Order order = orderRepository.findById(1L);

        //when
        List<Dish> orderedDishes = dishRepository.findAllDishesInOrderById(order.getId());

        //then
        Assert.assertEquals(3, orderedDishes.size());
    }

    @Test
    public void getCompositionsByDishIdTest_validData_shouldFindDishCompositions() {
        //given && when
        List<Composition> dishCompositions = dishRepository.getCompositionsByDishId(1L);

        //then
        Assert.assertEquals(3, dishCompositions.size());
    }

    @Test
    public void findAllOrdersThatIncludeDishByDishIdTest_validData_shouldFindOrdersThatIncludeDish() {
        //given && when
        List<Order> orders = dishRepository.findAllOrdersThatIncludeDishByDishId(1L);

        //then
        Assert.assertEquals(4, orders.size());
    }
}