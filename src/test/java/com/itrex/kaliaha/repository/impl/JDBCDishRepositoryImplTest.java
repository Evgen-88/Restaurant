package com.itrex.kaliaha.repository.impl;

import com.itrex.kaliaha.entity.Dish;
import com.itrex.kaliaha.entity.Order;
import com.itrex.kaliaha.entity.User;
import com.itrex.kaliaha.entity.util.Group;
import com.itrex.kaliaha.entity.util.OrderStatus;
import com.itrex.kaliaha.repository.BaseRepositoryTest;
import com.itrex.kaliaha.repository.DishRepository;
import com.itrex.kaliaha.repository.OrderRepository;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JDBCDishRepositoryImplTest extends BaseRepositoryTest {
    private final DishRepository dishRepository;
    private final OrderRepository orderRepository;
    private final List<Dish> dishes;

    public JDBCDishRepositoryImplTest() {
        super();
        dishRepository = new JDBCDishRepositoryImpl(getConnectionPool());
        orderRepository = new JDBCOrderRepositoryImpl(getConnectionPool());
        dishes = new ArrayList<>() {{
            add(new Dish(1L,"Картошка с грибами", 2, Group.Горячее, "Очень вкусно", "photo.img"));
            add(new Dish(2L,"Салат по-французски", 7, Group.Салаты, "Невкусно", "photo1.img"));
            add(new Dish(3L,"Макароны по-европейски", 11, Group.Напитки, "Невероятно", "photo2.img"));
        }};
    }

    @Test
    public void selectById_validData_shouldReturnDishById() {
        //given
        Dish expected = dishes.get(0);
        //when
        Dish actual = dishRepository.selectById(expected.getId());
        //then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void selectAll_validData_shouldReturnAllDishes() {
        //given && //when
        List<Dish> actual = dishRepository.selectAll();
        //then
        Assert.assertEquals(dishes, actual);
    }

    @Test
    public void add_validData_shouldReturnDishById() {
        //given
        Dish expected = new Dish(4L, "Макароны по-немецки", 2, Group.Горячее, "Nice dish", "not image");
        //when
        Dish actual = new Dish(-1L, "Макароны по-немецки", 2, Group.Горячее, "Nice dish", "not image");
        dishRepository.add(actual);
        //then
        Assert.assertEquals(expected, actual);
        Assert.assertEquals(expected, dishRepository.selectById(actual.getId()));
    }

    @Test
    public void addAll_validData_shouldReturnAllDishes() {
        //given
        List<Dish> expected = new ArrayList<>() {{
            add(new Dish(4L, "Макароны по-немецки", 2, Group.Горячее, "Nice dish", "not image"));
            add(new Dish(5L, "Макароны по-испански", 1, Group.Напитки, "Найс dish", "way it is not image"));

        }};
        //when
        List<Dish> actual = new ArrayList<>() {{
            add(new Dish(-1L, "Макароны по-немецки", 2, Group.Горячее, "Nice dish", "not image"));
            add(new Dish(-1L, "Макароны по-испански", 1, Group.Напитки, "Найс dish", "way it is not image"));
        }};
        dishRepository.addAll(actual);
        //then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void update_validData_shouldUpdateAndReturnNewDish() {
        //given
        Dish expected = new Dish(1L, "Шаньга", 2, Group.Горячее, "Ужасно", "photo1111.img");
        Dish actual = dishes.get(0);
        //when
        actual.setDishName("Шаньга");
        actual.setGroup(Group.Горячее);
        actual.setDescription("Ужасно");
        actual.setImagePath("photo1111.img");
        boolean result = dishRepository.update(actual);
        //then
        Assert.assertTrue(result);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void remove_validData_shouldRemoveDish() {
        //given
        Dish expected = new Dish(4L, "Шаньга", 2, Group.Горячее, "Ужасно", "photo1111.img");
        Dish newDish = new Dish("Шаньга", 2, Group.Горячее, "Ужасно", "photo1111.img");
        dishRepository.add(newDish);
        Assert.assertEquals(expected, newDish);
        //when
        boolean actual = dishRepository.remove(newDish.getId());
        //then
        Assert.assertTrue(actual);
    }

    @Test
    public void findAllDishesInOrderById_validData_shouldFindAllDishesInOrder() {
        //given
        User user = new User(1L, "Коляго", "Владислав", "kaliaha.vladzislav", "1111", "г.Витебск");
        Order expected = new Order(7L, 15, LocalDate.of(2021, 10, 20), "г. Минск", OrderStatus.New, user);
        Order newOrder = new Order(15, LocalDate.of(2021, 10, 20), "г. Минск", OrderStatus.New, user);
        orderRepository.add(newOrder);
        Assert.assertEquals(expected, newOrder);
        //when
        Assert.assertTrue(orderRepository.orderDish(newOrder, 1L));
        Assert.assertTrue(orderRepository.orderDish(newOrder, 2L));
        List<Dish> orderedDishesExpected = new ArrayList<>() {{
            add(dishes.get(0));
            add(dishes.get(1));
        }};
        //then
        List<Dish> orderedDishesActual = dishRepository.findAllDishesInOrderById(newOrder.getId());
        Assert.assertEquals(orderedDishesExpected, orderedDishesActual);
    }
}