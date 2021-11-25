package com.itrex.kaliaha.controller;

import com.itrex.kaliaha.dto.*;
import com.itrex.kaliaha.exception.DishIsNotOrderedException;
import com.itrex.kaliaha.exception.InvalidIdParameterServiceException;
import com.itrex.kaliaha.exception.ServiceException;
import com.itrex.kaliaha.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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

    @PostMapping
    public ResponseEntity<?> addOrder(@RequestBody OrderSaveOrUpdateDTO orderSaveOrUpdateDTO) {
        try {
            return new ResponseEntity<>(orderService.add(orderSaveOrUpdateDTO), HttpStatus.OK);
        } catch (ServiceException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateOrder(@RequestBody OrderSaveOrUpdateDTO orderSaveOrUpdateDTO) {
        try {
            return new ResponseEntity<>(orderService.update(orderSaveOrUpdateDTO), HttpStatus.OK);
        } catch (ServiceException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(orderService.delete(id), HttpStatus.OK);
        } catch (InvalidIdParameterServiceException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{orderId}/dishes/{dishId}")
    public ResponseEntity<?> addDishToOrder(@PathVariable Long orderId, @PathVariable  Long dishId) {
        try {
            return new ResponseEntity<>(orderService.addDishToOrder(orderId, dishId), HttpStatus.OK);
        } catch (ServiceException | DishIsNotOrderedException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{orderId}/dishes/{dishId}")
    public ResponseEntity<?> deleteDishFromOrder(@PathVariable Long orderId, @PathVariable  Long dishId) {
        try {
            return new ResponseEntity<>(orderService.deleteDishFromOrder(orderId, dishId), HttpStatus.OK);
        } catch (DishIsNotOrderedException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}