package com.itrex.kaliaha.repository.impl;

import com.itrex.kaliaha.entity.Order;
import com.itrex.kaliaha.enums.OrderStatus;
import com.itrex.kaliaha.repository.BaseRepositoryTest;
import com.itrex.kaliaha.repository.OrderRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class JDBCOrderRepositoryImplTest extends BaseRepositoryTest {
    private final OrderRepository orderRepository;
    private List<Order> orders;

    public JDBCOrderRepositoryImplTest() {
        super();
        orderRepository = new JDBCOrderRepositoryImpl(getConnectionPool());
    }

    @Before
    public void init() {
        orders = new ArrayList<>() {{
            add(new Order(1L, 1500, LocalDate.of(2021, 10, 21), "г. Минск", OrderStatus.COOKING, 1L));
            add(new Order(2L, 2800, LocalDate.of(2021, 10, 22), "г. Минск", OrderStatus.COOKING, 2L));
            add(new Order(3L, 1200, LocalDate.of(2021, 10, 23), "г. Витебск", OrderStatus.COOKING, 1L));
            add(new Order(4L, 1500, LocalDate.of(2021, 11, 24), "г. Минск", OrderStatus.NEW, 3L));
            add(new Order(5L, 2800, LocalDate.of(2021, 11, 25), "г. Минск", OrderStatus.NEW, 4L));
            add(new Order(6L, 1200, LocalDate.of(2021, 12, 26), "г. Витебск", OrderStatus.NEW, 1L));
        }};
    }

    @Test
    public void findById_validData_shouldReturnExistOrderById() {
        //given
        Order expected = orders.get(0);
        //when
        Order actual = orderRepository.findById(expected.getId());
        //then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findAll_validData_shouldReturnExistAllOrders() {
        //given
        List<Order> expected = orders;
        //when
        List<Order> actual = orderRepository.findAll();
        //then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void add_validData_shouldAddOneOrder() {
        //given
        Order expected = new Order(7L, 1500, LocalDate.of(2021, 12, 27), "г. Минск", OrderStatus.NEW, 1L);
        //when
        Order actual = new Order(1500, LocalDate.of(2021, 12, 27), "г. Минск", OrderStatus.NEW, 1L);
        orderRepository.add(actual);
        //then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void addAll_validData_shouldAddAllOrders() {
        //given
        List<Order> expected = new ArrayList<>() {{
            add(new Order(7L, 1500, LocalDate.of(2021, 12, 27), "г. Минск", OrderStatus.NEW, 1L));
            add(new Order(8L, 2800, LocalDate.of(2021, 12, 28), "г. Минск", OrderStatus.COOKING, 2L));
        }};
        //when
        List<Order> actual = new ArrayList<>() {{
            add(new Order(1500, LocalDate.of(2021, 12, 27), "г. Минск", OrderStatus.NEW, 1L));
            add(new Order(2800, LocalDate.of(2021, 12, 28), "г. Минск", OrderStatus.COOKING, 2L));
        }};
        orderRepository.addAll(actual);
        //then
        assertEquals(expected, actual);
    }

    @Test
    public void update_validData_shouldUpdateOrder() {
        //given
        Order expected = new Order(1L, 1600, LocalDate.of(2021, 11, 1), "updated г. Минск", OrderStatus.COMPLETED, 1L);
        Order actual = orders.get(0);
        //when
        actual.setPrice(1600);
        actual.setDate(LocalDate.of(2021, 11, 1));
        actual.setAddress("updated г. Минск");
        actual.setOrderStatus(OrderStatus.COMPLETED);
        boolean result = orderRepository.update(actual);
        //then
        Assert.assertTrue(result);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void delete_validData_shouldDeleteOrder() {
        //given
        Order checkAddition = new Order(7L, 15, LocalDate.of(2021, 10, 20), "г. Минск", OrderStatus.NEW, 1L);
        Order newOrder = new Order(15, LocalDate.of(2021, 10, 20), "г. Минск", OrderStatus.NEW, 1L);
        orderRepository.add(newOrder);
        Assert.assertEquals(checkAddition, newOrder);
        //when
        boolean actual = orderRepository.delete(newOrder.getId());
        //then
        Assert.assertTrue(actual);
    }

    @Test
    public void findOrdersByUserId_validData_shouldReturnListUserOrders() {
        //given
        List<Order> expected = orders.stream().filter(order -> order.getUserId() == 1).collect(Collectors.toList());
        //when
        List<Order> actual = orderRepository.findOrdersByUserId(1L);
        //then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void orderDish_validData_shouldAddDishToOrder() {
        //given && when && then
        boolean actual = orderRepository.orderDish(orders.get(1), 3L);
        Assert.assertTrue(actual);
    }

    @Test
    public void deleteDishesByOrderId_validData_shouldDeleteAllDishesInOrder() {
        //given && when
        Order checkAddition = new Order(7L, 3000, LocalDate.of(2021, 12, 30), "г. Минск", OrderStatus.NEW, 2L);
        Order newOrder = new Order(3000, LocalDate.of(2021, 12, 30), "г. Минск", OrderStatus.NEW, 2L);
        orderRepository.add(newOrder);
        Assert.assertEquals(checkAddition, newOrder);

        Assert.assertTrue(orderRepository.orderDish(newOrder, 1L));
        Assert.assertTrue(orderRepository.orderDish(newOrder, 2L));
        Assert.assertTrue(orderRepository.orderDish(newOrder, 3L));
        Assert.assertTrue(orderRepository.deleteDishesByOrderId(newOrder.getId()));
    }
}