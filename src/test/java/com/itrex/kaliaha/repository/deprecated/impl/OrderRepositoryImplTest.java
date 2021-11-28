package com.itrex.kaliaha.repository.deprecated.impl;

import com.itrex.kaliaha.entity.Dish;
import com.itrex.kaliaha.entity.Order;
import com.itrex.kaliaha.entity.User;
import com.itrex.kaliaha.enums.OrderStatus;
import com.itrex.kaliaha.exception.RepositoryException;
import com.itrex.kaliaha.repository.BaseRepositoryTest;
import com.itrex.kaliaha.repository.deprecated.DishRepository;
import com.itrex.kaliaha.repository.deprecated.OrderRepository;
import com.itrex.kaliaha.repository.deprecated.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderRepositoryImplTest extends BaseRepositoryTest {
    private final List<Order> orders;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DishRepository dishRepository;

    public OrderRepositoryImplTest() {
        orders = new ArrayList<>() {{
            add(Order.builder().id(1L).price(1500).date(LocalDate.of(2021, 10, 21)).address("г. Минск").orderStatus(OrderStatus.COOKING).user(User.builder().id(1L).build()).build());
            add(Order.builder().id(2L).price(2800).date(LocalDate.of(2021, 10, 22)).address("г. Минск").orderStatus(OrderStatus.COOKING).user(User.builder().id(2L).build()).build());
            add(Order.builder().id(3L).price(1200).date(LocalDate.of(2021, 10, 23)).address("г. Витебск").orderStatus(OrderStatus.COOKING).user(User.builder().id(1L).build()).build());
            add(Order.builder().id(4L).price(1500).date(LocalDate.of(2021, 11, 24)).address("г. Минск").orderStatus(OrderStatus.NEW).user(User.builder().id(3L).build()).build());
            add(Order.builder().id(5L).price(2800).date(LocalDate.of(2021, 11, 25)).address("г. Минск").orderStatus(OrderStatus.NEW).user(User.builder().id(4L).build()).build());
            add(Order.builder().id(6L).price(1200).date(LocalDate.of(2021, 12, 26)).address("г. Витебск").orderStatus(OrderStatus.NEW).user(User.builder().id(1L).build()).build());
        }};
    }

    @Test
    public void findByIdTest_validData_shouldReturnOrderById() throws RepositoryException {
        //given
        Order expected = orders.get(0);

        //when
        Order actual = orderRepository.findById(expected.getId());

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void findAllTest_validData_shouldReturnAllExistingOrders() throws RepositoryException {
        //given && //when
        List<Order> actual = orderRepository.findAll();

        //then
        Assertions.assertEquals(orders, actual);
    }

    @Test
    public void addTest_validData_shouldAddOrder() throws RepositoryException {
        //given
        List<Order> expected = orderRepository.findAll();

        Assertions.assertEquals(orders.size(), expected.size());

        //when
        Order newActual = Order.builder().price(1500).date(LocalDate.of(2021, 12, 27)).address("г. Минск").orderStatus(OrderStatus.NEW).user(User.builder().id(1L).build()).build();
        Order addedOrder = orderRepository.add(newActual);
        Order newExpected = Order.builder().id(7L).price(1500).date(LocalDate.of(2021, 12, 27)).address("г. Минск").orderStatus(OrderStatus.NEW).user(User.builder().id(1L).build()).build();
        expected.add(newExpected);

        //then
        Assertions.assertNotNull(addedOrder.getId());
        Assertions.assertEquals(newExpected, newActual);
        Assertions.assertEquals(newExpected, orderRepository.findById(newActual.getId()));
    }

    @Test
    public void updateTest_validData_shouldUpdateOrder() throws RepositoryException {
        //given

        Order expected = Order.builder().id(1L).price(1600).date(LocalDate.of(2021, 11, 1)).address("updated г. Минск").orderStatus(OrderStatus.COMPLETED).user(User.builder().id(1L).build()).build();
        Order actual = orderRepository.findById(expected.getId());

        Assertions.assertEquals(expected.getId(), actual.getId());

        //when
        actual = Order.builder().id(expected.getId()).price(1600).date(LocalDate.of(2021, 11, 1)).address("updated г. Минск").orderStatus(OrderStatus.COMPLETED).user(User.builder().id(1L).build()).build();
        Order updatedOrder = orderRepository.update(actual);

        //then
        Assertions.assertEquals(expected, updatedOrder);
        Assertions.assertEquals(expected, orderRepository.findById(updatedOrder.getId()));
    }

    @Test
    public void deleteTest_validData_shouldDeleteOrder() throws RepositoryException {
        //given
        Order expected = orders.get(0);
        Order actual = orderRepository.findById(1L);

        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(3, dishRepository.findAllDishesInOrderById(actual.getId()).size());

        //when
        boolean isDeleted = orderRepository.delete(actual.getId());

        //then
        Assertions.assertTrue(isDeleted);
        Assertions.assertThrows(RepositoryException.class, () -> orderRepository.findById(1L));
        Assertions.assertEquals(0, dishRepository.findAllDishesInOrderById(actual.getId()).size());
    }

    @Test
    public void findOrdersByUserIdTest_validData_shouldReturnListUserOrders() throws RepositoryException {
        //given && when
        List<Order> orders = orderRepository.findOrdersByUserId(1L);

        //then
        Assertions.assertEquals(3, orders.size());
    }


    @Test
    public void findAllOrdersThatIncludeDishByDishIdTest_validData_shouldFindOrdersThatIncludeDish() throws RepositoryException {
        //given && when
        List<Order> orders = orderRepository.findAllOrdersThatIncludeDishByDishId(1L);

        //then
        Assertions.assertEquals(4, orders.size());
    }

    @Test
    public void addDishToOrderTest_validData_shouldAddDishToOrder() throws RepositoryException {
        //given
        User user = userRepository.findById(1L);
        List<Order> orders = orderRepository.findOrdersByUserId(user.getId());
        List<Dish> orderedDishesExpected = dishRepository.findAllDishesInOrderById(1L);

        Assertions.assertEquals(3, orders.size());
        Assertions.assertEquals(3, orderedDishesExpected.size());

        //when
        boolean isOrdered = orderRepository.addDishToOrder(1L, 1L);

        //then
        Assertions.assertTrue(isOrdered);
        Assertions.assertEquals(4, dishRepository.findAllDishesInOrderById(1L).size());
    }

    @Test
    public void deleteDishFromOrderTest_validData_shouldDeleteDishInOrder() throws RepositoryException {
        //given
        User user = userRepository.findById(1L);
        List<Order> orders = orderRepository.findOrdersByUserId(user.getId());
        List<Dish> orderedDishesExpected = dishRepository.findAllDishesInOrderById(1L);

        Assertions.assertEquals(3, orders.size());
        Assertions.assertEquals(3, orderedDishesExpected.size());

        //when
        Dish dishToDelete = orderedDishesExpected.remove(0);
        boolean isDeleted = orderRepository.deleteDishFromOrder(1L, dishToDelete.getId());

        //then
        Assertions.assertTrue(isDeleted);
        Assertions.assertEquals(orderedDishesExpected, dishRepository.findAllDishesInOrderById(1L));
    }
}