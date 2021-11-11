package com.itrex.kaliaha.converters;

import com.itrex.kaliaha.dto.OrderDTO;
import com.itrex.kaliaha.dto.OrderListDTO;
import com.itrex.kaliaha.entity.Order;
import com.itrex.kaliaha.entity.User;

import java.util.List;
import java.util.stream.Collectors;

public class OrderConverter {
    public static OrderDTO toDTO(Order order) {
        return OrderDTO.builder()
                .orderId(order.getId())
                .price(order.getPrice())
                .date(order.getDate())
                .address(order.getAddress())
                .orderStatus(order.getOrderStatus())
                .orderedDishes(DishConverter.toDishListDTO(order.getDishes()))
                .userListDTO(UserConverter.toListDTO(order.getUser()))
                .build();
    }

    public static OrderListDTO toListDTO(Order order) {
        return OrderListDTO.builder()
                .user(UserConverter.toListDTO(order.getUser()))
                .orderId(order.getId())
                .price(order.getPrice())
                .date(order.getDate())
                .address(order.getAddress())
                .orderStatus(order.getOrderStatus())
                .build();
    }

    public static List<OrderListDTO> toOrderListDTO(List<Order> orders) {
        return orders.stream().map(OrderConverter::toListDTO).collect(Collectors.toList());
    }

    public static Order fromListDTO(OrderListDTO orderListDTO) {
        return Order.builder()
                .id(orderListDTO.getOrderId())
                .date(orderListDTO.getDate())
                .orderStatus(orderListDTO.getOrderStatus())
                .address(orderListDTO.getAddress())
                .price(orderListDTO.getPrice())
                .user(User.builder().id(orderListDTO.getUser().getId()).build())
                .build();
    }
}