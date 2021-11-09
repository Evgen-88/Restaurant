package com.itrex.kaliaha.converters;

import com.itrex.kaliaha.dto.OrderDTO;
import com.itrex.kaliaha.dto.OrderListDTO;
import com.itrex.kaliaha.entity.Dish;
import com.itrex.kaliaha.entity.Order;
import com.itrex.kaliaha.entity.User;

import java.util.List;
import java.util.stream.Collectors;

public class OrderConverter {
    public static OrderDTO toDTO(Order order, List<Dish> dishes) {
        return OrderDTO.builder()
                .userListDTO(UserConverter.toDTO(order.getUser()))
                .orderId(order.getId())
                .price(order.getPrice())
                .date(order.getDate())
                .address(order.getAddress())
                .orderStatus(order.getOrderStatus())
                .orderedDishes(DishConverter.toDishListDTO(dishes))
                .build();
    }



    public static OrderListDTO toDTO(Order order) {
        return OrderListDTO.builder()
                .user(UserConverter.toDTO(order.getUser()))
                .orderId(order.getId())
                .price(order.getPrice())
                .date(order.getDate())
                .address(order.getAddress())
                .orderStatus(order.getOrderStatus())
                .build();
    }

    public static List<OrderListDTO> toOrderListDTO(List<Order> orders) {
        return orders.stream()
                .map(OrderConverter::toDTO)
                .collect(Collectors.toList());
    }

    public static Order fromDTO(OrderDTO orderDTO) {
        Order order = new Order();
        order.setId(orderDTO.getOrderId());
        order.setDate(orderDTO.getDate());
        order.setOrderStatus(orderDTO.getOrderStatus());
        order.setAddress(orderDTO.getAddress());
        order.setPrice(orderDTO.getPrice());
        order.setUser(new User(orderDTO.getUserListDTO().getId()));
        return order;
    }
}