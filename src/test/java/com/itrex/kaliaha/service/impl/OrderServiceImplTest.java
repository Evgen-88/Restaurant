package com.itrex.kaliaha.service.impl;

import com.itrex.kaliaha.converters.OrderConverter;
import com.itrex.kaliaha.dto.OrderDTO;
import com.itrex.kaliaha.dto.OrderListDTO;
import com.itrex.kaliaha.dto.OrderSaveOrUpdateDTO;
import com.itrex.kaliaha.entity.Dish;
import com.itrex.kaliaha.entity.Order;
import com.itrex.kaliaha.entity.User;
import com.itrex.kaliaha.enums.OrderStatus;
import com.itrex.kaliaha.exception.ServiceException;
import com.itrex.kaliaha.repository.OrderRepository;
import com.itrex.kaliaha.service.BaseServiceTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

class OrderServiceImplTest extends BaseServiceTest {
    @InjectMocks
    private OrderServiceImpl orderService;
    @Mock
    private OrderRepository orderRepository;

    public Order getOrderFindById() {
        User user = getUsers().get(0);
        List<Dish> dishes = new ArrayList<>() {{
            add(getDishes().get(0));
            add(getDishes().get(2));
        }};
        Order order = getOrders().get(0);
        order.setUser(user);
        order.setDishes(dishes);
        return order;
    }

    public OrderDTO getOrderDTO() {
        return OrderConverter.toDTO(getOrderFindById());
    }

    @Test
    void findByIdTest_shouldReturnOrderDTO() {
        //given
        OrderDTO expected = getOrderDTO();

        // when
        when(orderRepository.findById(1L)).thenReturn(getOrderFindById());
        OrderDTO actual = orderService.findById(1L);

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAllTest_shouldReturnAllOrderListDTO() {
        //given
        List<OrderListDTO> expected = getListOrderListDTO();

        // when
        when(orderRepository.findAll()).thenReturn(getOrders());
        List<OrderListDTO> actual = orderService.findAll();

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void addTest_shouldAddNewOrder() throws ServiceException {
        //given
        when(orderRepository.findAll()).thenReturn(getOrders());
        List<OrderListDTO> actualList = orderService.findAll();

        Assertions.assertEquals(5, actualList.size());

        // when
        OrderSaveOrUpdateDTO expected = OrderSaveOrUpdateDTO.builder().price(1500).date(Date.valueOf(LocalDate.of(2021, 12, 27))).address("г. Минск").orderStatus(OrderStatus.NEW).userId(1L).build();

        Order beforeAdd = Order.builder().price(1500).date(LocalDate.of(2021, 12, 27)).address("г. Минск").orderStatus(OrderStatus.NEW).user(User.builder().id(1L).build()).build();
        Order afterAdd = Order.builder().id(6L).price(1500).date(LocalDate.of(2021, 12, 27)).address("г. Минск").orderStatus(OrderStatus.NEW).user(User.builder().id(1L).build()).build();

        when(orderRepository.add(beforeAdd)).thenReturn(afterAdd);
        OrderSaveOrUpdateDTO actual = orderService.add(expected);

        //then
        Assertions.assertNotNull(actual.getOrderId());
        expected.setOrderId(6L);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void updateTest_shouldUpdateOrder() throws ServiceException {
        //given
        OrderSaveOrUpdateDTO expected = OrderSaveOrUpdateDTO.builder().orderId(1L).price(1600).date(Date.valueOf(LocalDate.of(2021, 11, 1))).address("updated г. Минск").orderStatus(OrderStatus.COMPLETED).userId(1L).build();
        Order toUpdate = Order.builder().id(1L).price(1600).date(LocalDate.of(2021, 11, 1)).address("updated г. Минск").orderStatus(OrderStatus.COMPLETED).user(User.builder().id(1L).build()).build();

        //when
        when(orderRepository.update(toUpdate)).thenReturn(toUpdate);
        OrderSaveOrUpdateDTO actual = orderService.update(expected);

        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void deleteTest_shouldDeleteDish() {
        //given && when && then
        when(orderRepository.delete(1L)).thenReturn(true);
        Assertions.assertDoesNotThrow(() -> orderService.delete(1L));
    }

    @Test
    void addDishToOrderTest_shouldAddDishToOrder() {
        //given && when && then
        when(orderRepository.addDishToOrder(1L, 1L)).thenReturn(true);
        Assertions.assertDoesNotThrow(() -> orderService.addDishToOrder(1L, 1L));
    }

    @Test
    void deleteFromOrderDishByIdTest_shouldDeleteDishFromOrder() {
        //given && when && then
        when(orderRepository.deleteDishFromOrder(1L, 1L)).thenReturn(true);
        Assertions.assertDoesNotThrow(() -> orderService.deleteDishFromOrder(1L, 1L));
    }
}