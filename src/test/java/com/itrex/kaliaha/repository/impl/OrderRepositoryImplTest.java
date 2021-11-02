package com.itrex.kaliaha.repository.impl;

import com.itrex.kaliaha.entity.Dish;
import com.itrex.kaliaha.entity.Order;
import com.itrex.kaliaha.entity.User;
import com.itrex.kaliaha.enums.OrderStatus;
import com.itrex.kaliaha.repository.BaseRepositoryTest;
import com.itrex.kaliaha.repository.DishRepository;
import com.itrex.kaliaha.repository.OrderRepository;
import com.itrex.kaliaha.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderRepositoryImplTest extends BaseRepositoryTest {
    private final OrderRepository orderRepository;
    private final List<Order> orders;

    public OrderRepositoryImplTest() {
        orderRepository = getApplicationContext().getBean(OrderRepositoryImpl.class);
        orders = new ArrayList<>() {{
            add(new Order(1L, 1500, LocalDate.of(2021, 10, 21), "г. Минск", OrderStatus.COOKING, new User(1L)));
            add(new Order(2L, 2800, LocalDate.of(2021, 10, 22), "г. Минск", OrderStatus.COOKING, new User(2L)));
            add(new Order(3L, 1200, LocalDate.of(2021, 10, 23), "г. Витебск", OrderStatus.COOKING, new User(1L)));
            add(new Order(4L, 1500, LocalDate.of(2021, 11, 24), "г. Минск", OrderStatus.NEW, new User(3L)));
            add(new Order(5L, 2800, LocalDate.of(2021, 11, 25), "г. Минск", OrderStatus.NEW, new User(4L)));
            add(new Order(6L, 1200, LocalDate.of(2021, 12, 26), "г. Витебск", OrderStatus.NEW, new User(1L)));
        }};
    }

    @Test
    public void findByIdTest_validData_shouldReturnOrderById() {
        //given
        Order expected = orders.get(0);

        //when
        Order actual = orderRepository.findById(expected.getId());

        //then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findAllTest_validData_shouldReturnAllExistingOrders() {
        //given && //when
        List<Order> actual = orderRepository.findAll();

        //then
        Assert.assertEquals(orders, actual);
    }

    @Test
    public void addTest_validData_shouldAddOrder() {
        //given
        List<Order> expected = orderRepository.findAll();

        Assert.assertEquals(orders.size(), expected.size());

        //when
        Order newActual = new Order(1500, LocalDate.of(2021, 12, 27), "г. Минск", OrderStatus.NEW, new User(1L));
        boolean isAdded = orderRepository.add(newActual);
        Order newExpected = new Order(7L, 1500, LocalDate.of(2021, 12, 27), "г. Минск", OrderStatus.NEW, new User(1L));
        expected.add(newExpected);

        //then
        Assert.assertTrue(isAdded);
        Assert.assertEquals(newExpected, newActual);
        Assert.assertEquals(newExpected, orderRepository.findById(newActual.getId()));
    }

    @Test
    public void updateTest_validData_shouldUpdateOrder() {
        //given
        Order expected = new Order(1L, 1600, LocalDate.of(2021, 11, 1), "updated г. Минск", OrderStatus.COMPLETED, new User(1L));
        Order actual = orderRepository.findById(expected.getId());

        Assert.assertEquals(expected.getId(), actual.getId());

        //when
        actual.setPrice(1600);
        actual.setDate(LocalDate.of(2021, 11, 1));
        actual.setAddress("updated г. Минск");
        actual.setOrderStatus(OrderStatus.COMPLETED);
        actual.setUser(new User(1L));
        boolean isUpdated = orderRepository.update(actual);

        //then
        Assert.assertTrue(isUpdated);
        Assert.assertEquals(expected, actual);
        Assert.assertEquals(expected, orderRepository.findById(actual.getId()));
    }

    @Test
    public void deleteTest_validData_shouldDeleteOrder() {
        DishRepository dishRepository = getApplicationContext().getBean(DishRepositoryImpl.class);
        //given
        Order expected = orders.get(0);
        Order actual = orderRepository.findById(1L);

        Assert.assertEquals(expected, actual);
        Assert.assertEquals(3, dishRepository.findAllDishesInOrderById(actual.getId()).size());

        //when
        boolean isDeleted = orderRepository.delete(actual.getId());

        //then
        Assert.assertTrue(isDeleted);
        Assert.assertNull(orderRepository.findById(1L));
        Assert.assertEquals(0, dishRepository.findAllDishesInOrderById(actual.getId()).size());
    }

    @Test
    public void findOrdersByUserIdTest_validData_shouldReturnListUserOrders() {
        //given && when
        List<Order> orders = orderRepository.findOrdersByUserId(1L);

        //then
        Assert.assertEquals(3, orders.size());
    }

    @Test
    public void orderDishTest_validData_shouldAddDishToOrder() {
        UserRepository userRepository = getApplicationContext().getBean(UserRepositoryImpl.class);
        DishRepository dishRepository = getApplicationContext().getBean(DishRepositoryImpl.class);

        //given
        User user = userRepository.findById(1L);
        List<Order> orders = orderRepository.findOrdersByUserId(user.getId());
        Order actual = orders.get(0);
        List<Dish> orderedDishesExpected = dishRepository.findAllDishesInOrderById(actual.getId());

        Assert.assertEquals(3, orders.size());
        Assert.assertEquals(1L, (long) actual.getId());
        Assert.assertEquals(3, orderedDishesExpected.size());

        //when
        boolean isOrdered = orderRepository.orderDish(actual.getId(),1L);
        orderedDishesExpected.add(dishRepository.findById(1L));

        //then
        Assert.assertTrue(isOrdered);
        Assert.assertEquals(orderedDishesExpected, dishRepository.findAllDishesInOrderById(actual.getId()));
    }

    @Test
    public void deleteFromOrderDishByIdTest_validData_shouldDeleteDishInOrder() {
        UserRepository userRepository = getApplicationContext().getBean(UserRepositoryImpl.class);
        DishRepository dishRepository = getApplicationContext().getBean(DishRepositoryImpl.class);

        //given
        User user = userRepository.findById(1L);
        List<Order> orders = orderRepository.findOrdersByUserId(user.getId());
        Order actual = orders.get(0);
        List<Dish> orderedDishesExpected = dishRepository.findAllDishesInOrderById(actual.getId());

        Assert.assertEquals(3, orders.size());
        Assert.assertEquals(1L, (long) actual.getId());
        Assert.assertEquals(3, orderedDishesExpected.size());

        //when
        Dish dishToDelete = orderedDishesExpected.remove(0);
        boolean isDeleted = orderRepository.deleteFromOrderDishById(actual.getId(),dishToDelete.getId());

        //then
        Assert.assertTrue(isDeleted);
        Assert.assertEquals(orderedDishesExpected, dishRepository.findAllDishesInOrderById(actual.getId()));
    }
}