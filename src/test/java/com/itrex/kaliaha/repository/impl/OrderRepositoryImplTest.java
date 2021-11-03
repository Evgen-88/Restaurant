package com.itrex.kaliaha.repository.impl;

import com.itrex.kaliaha.config.ApplicationContextConfiguration;
import com.itrex.kaliaha.entity.Dish;
import com.itrex.kaliaha.entity.Order;
import com.itrex.kaliaha.entity.User;
import com.itrex.kaliaha.enums.OrderStatus;
import com.itrex.kaliaha.repository.BaseRepositoryTest;
import com.itrex.kaliaha.repository.DishRepository;
import com.itrex.kaliaha.repository.OrderRepository;
import com.itrex.kaliaha.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringJUnitConfig
@ContextConfiguration(classes = ApplicationContextConfiguration.class)
public class OrderRepositoryImplTest extends BaseRepositoryTest {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DishRepository dishRepository;

    private final List<Order> orders;

    public OrderRepositoryImplTest() {
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
       Assertions.assertEquals(expected, actual);
    }

    @Test
    public void findAllTest_validData_shouldReturnAllExistingOrders() {
        //given && //when
        List<Order> actual = orderRepository.findAll();

        //then
       Assertions.assertEquals(orders, actual);
    }

    @Test
    public void addTest_validData_shouldAddOrder() {
        //given
        List<Order> expected = orderRepository.findAll();

       Assertions.assertEquals(orders.size(), expected.size());

        //when
        Order newActual = new Order(1500, LocalDate.of(2021, 12, 27), "г. Минск", OrderStatus.NEW, new User(1L));
        boolean isAdded = orderRepository.add(newActual);
        Order newExpected = new Order(7L, 1500, LocalDate.of(2021, 12, 27), "г. Минск", OrderStatus.NEW, new User(1L));
        expected.add(newExpected);

        //then
        Assertions.assertTrue(isAdded);
       Assertions.assertEquals(newExpected, newActual);
       Assertions.assertEquals(newExpected, orderRepository.findById(newActual.getId()));
    }

    @Test
    public void updateTest_validData_shouldUpdateOrder() {
        //given
        Order expected = new Order(1L, 1600, LocalDate.of(2021, 11, 1), "updated г. Минск", OrderStatus.COMPLETED, new User(1L));
        Order actual = orderRepository.findById(expected.getId());

       Assertions.assertEquals(expected.getId(), actual.getId());

        //when
        actual.setPrice(1600);
        actual.setDate(LocalDate.of(2021, 11, 1));
        actual.setAddress("updated г. Минск");
        actual.setOrderStatus(OrderStatus.COMPLETED);
        actual.setUser(new User(1L));
        boolean isUpdated = orderRepository.update(actual);

        //then
        Assertions.assertTrue(isUpdated);
       Assertions.assertEquals(expected, actual);
       Assertions.assertEquals(expected, orderRepository.findById(actual.getId()));
    }

    @Test
    public void deleteTest_validData_shouldDeleteOrder() {
        //given
        Order expected = orders.get(0);
        Order actual = orderRepository.findById(1L);

       Assertions.assertEquals(expected, actual);
       Assertions.assertEquals(3, dishRepository.findAllDishesInOrderById(actual.getId()).size());

        //when
        boolean isDeleted = orderRepository.delete(actual.getId());

        //then
        Assertions.assertTrue(isDeleted);
        Assertions.assertNull(orderRepository.findById(1L));
       Assertions.assertEquals(0, dishRepository.findAllDishesInOrderById(actual.getId()).size());
    }

    @Test
    public void findOrdersByUserIdTest_validData_shouldReturnListUserOrders() {
        //given && when
        List<Order> orders = orderRepository.findOrdersByUserId(1L);

        //then
       Assertions.assertEquals(3, orders.size());
    }

    @Test
    public void orderDishTest_validData_shouldAddDishToOrder() {
        //given
        User user = userRepository.findById(1L);
        List<Order> orders = orderRepository.findOrdersByUserId(user.getId());
        Order actual = orders.get(0);
        List<Dish> orderedDishesExpected = dishRepository.findAllDishesInOrderById(actual.getId());

       Assertions.assertEquals(3, orders.size());
       Assertions.assertEquals(1L, (long) actual.getId());
       Assertions.assertEquals(3, orderedDishesExpected.size());

        //when
        boolean isOrdered = orderRepository.orderDish(actual.getId(),1L);
        orderedDishesExpected.add(dishRepository.findById(1L));

        //then
        Assertions.assertTrue(isOrdered);
       Assertions.assertEquals(orderedDishesExpected, dishRepository.findAllDishesInOrderById(actual.getId()));
    }

    @Test
    public void deleteFromOrderDishByIdTest_validData_shouldDeleteDishInOrder() {
        //given
        User user = userRepository.findById(1L);
        List<Order> orders = orderRepository.findOrdersByUserId(user.getId());
        Order actual = orders.get(0);
        List<Dish> orderedDishesExpected = dishRepository.findAllDishesInOrderById(actual.getId());

       Assertions.assertEquals(3, orders.size());
       Assertions.assertEquals(1L, (long) actual.getId());
       Assertions.assertEquals(3, orderedDishesExpected.size());

        //when
        Dish dishToDelete = orderedDishesExpected.remove(0);
        boolean isDeleted = orderRepository.deleteFromOrderDishById(actual.getId(),dishToDelete.getId());

        //then
        Assertions.assertTrue(isDeleted);
       Assertions.assertEquals(orderedDishesExpected, dishRepository.findAllDishesInOrderById(actual.getId()));
    }
}