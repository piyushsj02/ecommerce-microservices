package com.ecommerce.backend.service;

import com.ecommerce.backend.dto.OrderResponse;
import com.ecommerce.backend.entity.Order;
import com.ecommerce.backend.entity.Product;
import com.ecommerce.backend.repository.OrderRepository;
import com.ecommerce.backend.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    public Order placeOrder(Order order, String userEmail) {
        order.setUserEmail(userEmail);
        order.setOrderDate(LocalDateTime.now());
        return orderRepository.save(order);
    }

    public List<OrderResponse> getMyOrders(String email) {

        List<Order> orders = orderRepository.findByUserEmail(email);

        return orders.stream().map(order -> {

            Product product = productRepository
                    .findById(order.getProductId())
                    .orElseThrow();

            return new OrderResponse(
                    order.getId(),
                    order.getQuantity(),
                    order.getOrderDate().toString(),
                    product.getName(),
                    product.getPrice());

        }).toList();
    }
}