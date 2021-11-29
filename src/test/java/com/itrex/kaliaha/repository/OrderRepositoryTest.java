package com.itrex.kaliaha.repository;

import com.itrex.kaliaha.entity.Order;
import com.itrex.kaliaha.entity.User;
import com.itrex.kaliaha.enums.OrderStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderRepositoryTest extends BaseRepositoryTest {
    private final List<Order> orders;
    @Autowired
    private OrderRepository orderRepository;

    public OrderRepositoryTest() {
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
    public void findByIdTest_validData_shouldReturnOrderById() {
        //given
        Order expected = orders.get(0);

        //when
        Order actual = orderRepository.findById(expected.getId()).orElse(null);

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
        Order newActual = Order.builder().price(1500).date(LocalDate.of(2021, 12, 27)).address("г. Минск").orderStatus(OrderStatus.NEW).user(User.builder().id(1L).build()).build();
        Order addedOrder = orderRepository.save(newActual);
        Order newExpected = Order.builder().id(7L).price(1500).date(LocalDate.of(2021, 12, 27)).address("г. Минск").orderStatus(OrderStatus.NEW).user(User.builder().id(1L).build()).build();
        expected.add(newExpected);

        //then
        Assertions.assertNotNull(addedOrder.getId());
        Assertions.assertEquals(newExpected, newActual);
        Assertions.assertEquals(newExpected, orderRepository.findById(newActual.getId()).orElse(null));
    }

    @Test
    public void updateTest_validData_shouldUpdateOrder() {
        //given

        Order expected = Order.builder().id(1L).price(1600).date(LocalDate.of(2021, 11, 1)).address("updated г. Минск").orderStatus(OrderStatus.COMPLETED).user(User.builder().id(1L).build()).build();
        Order actual = orderRepository.findById(expected.getId()).orElse(Order.builder().build());

        Assertions.assertEquals(expected.getId(), actual.getId());

        //when
        actual = Order.builder().id(expected.getId()).price(1600).date(LocalDate.of(2021, 11, 1)).address("updated г. Минск").orderStatus(OrderStatus.COMPLETED).user(User.builder().id(1L).build()).build();
        Order updatedOrder = orderRepository.save(actual);

        //then
        Assertions.assertEquals(expected, updatedOrder);
        Assertions.assertEquals(expected, orderRepository.findById(updatedOrder.getId()).orElse(null));
    }
}