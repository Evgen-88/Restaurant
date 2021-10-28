package com.itrex.kaliaha.repository.impl;

import com.itrex.kaliaha.entity.*;
import com.itrex.kaliaha.enums.DishGroup;
import com.itrex.kaliaha.enums.OrderStatus;
import com.itrex.kaliaha.repository.BaseRepositoryTest;
import com.itrex.kaliaha.repository.DishRepository;
import com.itrex.kaliaha.repository.OrderRepository;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HibernateDishRepositoryImplTest extends BaseRepositoryTest {
    private final DishRepository dishRepository;
    private final OrderRepository orderRepository;
    private final List<Dish> dishes;

    public HibernateDishRepositoryImplTest() {
        super();
        dishRepository = new HibernateDishRepositoryImpl();
        orderRepository = new HibernateOrderRepositoryImpl();
        dishes = new ArrayList<>() {{
            add(new Dish(1L,"Картошка с грибами", 2, DishGroup.HOT, "Очень вкусно", "photo.img"));
            add(new Dish(2L,"Салат по-французски", 7, DishGroup.SALAD, "Невкусно", "photo1.img"));
            add(new Dish(3L,"Макароны по-европейски", 11, DishGroup.DRINK, "Невероятно", "photo2.img"));
        }};
    }

    @Test
    public void findById_validData_shouldReturnDishById() {
        //given
        Dish expected = dishes.get(0);
        //when
        Dish actual = dishRepository.findById(expected.getId());
        //then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findAll_validData_shouldReturnAllDishes() {
        //given && //when
        List<Dish> actual = dishRepository.findAll();
        //then
        Assert.assertEquals(dishes, actual);
    }

    @Test
    public void add_validData_shouldReturnDishById() {
        //given
        Dish expected = new Dish(4L, "Макароны по-немецки", 2, DishGroup.HOT, "Nice dish", "not image");
        //when
        Dish actual = new Dish("Макароны по-немецки", 2, DishGroup.HOT, "Nice dish", "not image");
        dishRepository.add(actual);
        //then
        Assert.assertEquals(expected, actual);
        Assert.assertEquals(expected, dishRepository.findById(actual.getId()));
    }

    @Test
    public void addAll_validData_shouldReturnAllDishes() {
        //given
        List<Dish> expected = new ArrayList<>() {{
            add(new Dish(4L, "Макароны по-немецки", 2, DishGroup.HOT, "Nice dish", "not image"));
            add(new Dish(5L, "Макароны по-испански", 1, DishGroup.DRINK, "Найс dish", "way it is not image"));

        }};
        //when
        List<Dish> actual = new ArrayList<>() {{
            add(new Dish("Макароны по-немецки", 2, DishGroup.HOT, "Nice dish", "not image"));
            add(new Dish("Макароны по-испански", 1, DishGroup.DRINK, "Найс dish", "way it is not image"));
        }};
        dishRepository.addAll(actual);
        //then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void update_validData_shouldUpdateAndReturnNewDish() {
        //given
        Dish expected = new Dish(1L, "Шаньга", 2, DishGroup.HOT, "Ужасно", "photo1111.img");
        Dish actual = dishes.get(0);
        //when
        actual.setDishName("Шаньга");
        actual.setDishGroup(DishGroup.HOT);
        actual.setDishDescription("Ужасно");
        actual.setImagePath("photo1111.img");
        boolean result = dishRepository.update(actual);
        //then
        Assert.assertTrue(result);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void delete_validData_shouldDeleteDish() {
        //given
        Dish expected = dishes.get(0);
        Dish actual = dishRepository.findById(1L);

        Assert.assertEquals(expected, actual);
        Assert.assertEquals(4, dishRepository.findAllOrdersThatIncludeDishById(actual.getId()).size());
        Assert.assertEquals(3, dishRepository.getDishCompositionById(actual.getId()).size());

        //when
        boolean isDeleted = dishRepository.delete(actual.getId());

        //then
        Assert.assertTrue(isDeleted);
        Assert.assertNull(dishRepository.findById(1L));
        Assert.assertEquals(0, dishRepository.findAllOrdersThatIncludeDishById(actual.getId()).size());
        Assert.assertEquals(0, dishRepository.getDishCompositionById(actual.getId()).size());
    }

    @Test
    public void findAllDishesInOrderById_validData_shouldFindAllDishesInOrder() {
        //given
        Order expected = new Order(7L, 15, LocalDate.of(2021, 10, 20), "г. Минск", OrderStatus.NEW, new User(1L));
        Order newOrder = new Order(15, LocalDate.of(2021, 10, 20), "г. Минск", OrderStatus.NEW, new User(1L));

        orderRepository.add(newOrder);
        Assert.assertEquals(expected, newOrder);
        //when
        Assert.assertTrue(orderRepository.orderDish(newOrder.getId(), 1L));
        Assert.assertTrue(orderRepository.orderDish(newOrder.getId(), 2L));
        List<Dish> orderedDishesExpected = new ArrayList<>() {{
            add(dishes.get(0));
            add(dishes.get(1));
        }};
        //then
        List<Dish> orderedDishesActual = dishRepository.findAllDishesInOrderById(newOrder.getId());
        Assert.assertEquals(orderedDishesExpected, orderedDishesActual);
    }
}