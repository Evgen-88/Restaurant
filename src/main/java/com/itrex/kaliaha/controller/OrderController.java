package com.itrex.kaliaha.controller;

import com.itrex.kaliaha.dto.*;
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

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController extends AbstractController{
    private final OrderService orderService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(orderService.findById(id), HttpStatus.OK);
        } catch (ServiceException ex) {
            return getResponseEntityForException(ex);
        }
    }

    @GetMapping
    public ResponseEntity<?> getOrders() {
        try {
            return new ResponseEntity<>(orderService.findAll(), HttpStatus.OK);
        } catch (ServiceException ex) {
            return getResponseEntityForException(ex);
        }
    }

    @PostMapping
    public ResponseEntity<?> addOrder(@RequestBody OrderSaveOrUpdateDTO orderSaveOrUpdateDTO) {
        try {
            return new ResponseEntity<>(orderService.add(orderSaveOrUpdateDTO), HttpStatus.OK);
        } catch (ServiceException ex) {
            return getResponseEntityForException(ex);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateOrder(@RequestBody OrderSaveOrUpdateDTO orderSaveOrUpdateDTO) {
        try {
            return new ResponseEntity<>(orderService.update(orderSaveOrUpdateDTO), HttpStatus.OK);
        } catch (ServiceException ex) {
            return getResponseEntityForException(ex);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(orderService.delete(id), HttpStatus.OK);
        } catch (ServiceException ex) {
            return getResponseEntityForException(ex);
        }
    }

    @GetMapping("/{id}/dishes")
    public ResponseEntity<?> getDishIngredients(@PathVariable Long id) {
        try{
            return new ResponseEntity<>(orderService.getDishesByOrderId(id), HttpStatus.OK);
        } catch (ServiceException ex) {
            return getResponseEntityForException(ex);
        }
    }

    @PutMapping("/{id}/dishes/{dishId}")
    public ResponseEntity<?> addDishToOrder(@PathVariable Long id, @PathVariable  Long dishId) {
        try {
            return new ResponseEntity<>(orderService.addDishToOrder(id, dishId), HttpStatus.OK);
        } catch (ServiceException ex) {
            return getResponseEntityForException(ex);
        }
    }

    @DeleteMapping("/{id}/dishes/{dishId}")
    public ResponseEntity<?> deleteDishFromOrder(@PathVariable Long id, @PathVariable  Long dishId) {
        try {
            return new ResponseEntity<>(orderService.deleteDishFromOrder(id, dishId), HttpStatus.OK);
        } catch (ServiceException ex) {
            return getResponseEntityForException(ex);
        }
    }
}