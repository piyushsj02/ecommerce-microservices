package com.ecommerce.backend.controller;

import com.ecommerce.backend.entity.Order;
import com.ecommerce.backend.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public Order placeOrder(@RequestBody Order order) {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return orderService.placeOrder(order, email);
    }

    @GetMapping
    public List<Order> getMyOrders() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return orderService.getMyOrders(email);
    }
}