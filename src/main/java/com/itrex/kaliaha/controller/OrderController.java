package com.itrex.kaliaha.controller;

import com.itrex.kaliaha.dto.*;
import com.itrex.kaliaha.exception.DishIsNotOrderedException;
import com.itrex.kaliaha.exception.InvalidIdParameterServiceException;
import com.itrex.kaliaha.exception.ServiceException;
import com.itrex.kaliaha.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/{id}")
    public OrderDTO getOrderById(@PathVariable Long id) {
        return orderService.findById(id);
    }

    @GetMapping
    public List<OrderListDTO> getOrders() {
        return orderService.findAll();
    }

    @PostMapping(params = {"price", "date", "address", "orderStatus", "userId"})
    public OrderSaveOrUpdateDTO addOrder(OrderSaveOrUpdateDTO orderSaveOrUpdateDTO) throws ServiceException {
        return orderService.add(orderSaveOrUpdateDTO);
    }

    @PutMapping(params = {"orderId", "price", "date", "address", "orderStatus", "userId"})
    public OrderSaveOrUpdateDTO updateOrder(OrderSaveOrUpdateDTO orderSaveOrUpdateDTO) throws ServiceException {
        return orderService.update(orderSaveOrUpdateDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) throws InvalidIdParameterServiceException {
        orderService.delete(id);
    }

    @PostMapping(params = {"orderId", "dishId"})
    public void addDishToOrder(Long orderId, Long dishId) throws ServiceException, DishIsNotOrderedException {
        orderService.addDishToOrder(orderId, dishId);
    }

    @DeleteMapping("/{orderId}/{dishId}")
    public void deleteDishFromOrder(@PathVariable Long orderId, @PathVariable Long dishId) throws DishIsNotOrderedException {
        orderService.deleteDishFromOrder(orderId, dishId);
    }
}