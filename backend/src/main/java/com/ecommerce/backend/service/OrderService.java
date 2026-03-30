package com.ecommerce.backend.service;

import com.ecommerce.backend.entity.Order;
import com.ecommerce.backend.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repo;

    public Order placeOrder(Order order, String userEmail) {
        order.setUserEmail(userEmail);
        order.setOrderDate(LocalDateTime.now());
        return repo.save(order);
    }

    public List<Order> getMyOrders(String userEmail) {
        return repo.findByUserEmail(userEmail);
    }
}