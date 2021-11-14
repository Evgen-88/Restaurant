package com.itrex.kaliaha.converters;

import com.itrex.kaliaha.dto.OrderDTO;
import com.itrex.kaliaha.dto.OrderListDTO;
import com.itrex.kaliaha.dto.OrderSaveOrUpdateDTO;
import com.itrex.kaliaha.dto.UserOrderListDTO;
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

    public static OrderSaveOrUpdateDTO toSaveOrUpdateDTO(Order order) {
        return OrderSaveOrUpdateDTO.builder()
                .userId(order.getUser().getId())
                .orderId(order.getId())
                .price(order.getPrice())
                .date(order.getDate())
                .address(order.getAddress())
                .orderStatus(order.getOrderStatus())
                .build();
    }

    public static Order toSaveOrUpdateDTO(OrderSaveOrUpdateDTO saveOrUpdateDTO) {
        return Order.builder()
                .id(saveOrUpdateDTO.getOrderId())
                .price(saveOrUpdateDTO.getPrice())
                .date(saveOrUpdateDTO.getDate())
                .address(saveOrUpdateDTO.getAddress())
                .orderStatus(saveOrUpdateDTO.getOrderStatus())
                .user(User.builder().id(saveOrUpdateDTO.getUserId()).build())
                .build();
    }

    public static UserOrderListDTO toUserOrderListDTO(Order order) {
        return UserOrderListDTO.builder()
                .orderId(order.getId())
                .price(order.getPrice())
                .date(order.getDate())
                .address(order.getAddress())
                .orderStatus(order.getOrderStatus())
                .build();
    }

    public static List<UserOrderListDTO> toListUserOrderListDTO(List<Order> orders) {
        return orders.stream().map(OrderConverter::toUserOrderListDTO).collect(Collectors.toList());
    }
}