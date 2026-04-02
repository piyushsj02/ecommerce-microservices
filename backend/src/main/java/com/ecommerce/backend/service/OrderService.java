package com.ecommerce.backend.service;

import com.ecommerce.backend.dto.OrderResponse;
import com.ecommerce.backend.entity.Cart;
import com.ecommerce.backend.entity.Order;
import com.ecommerce.backend.entity.Product;
import com.ecommerce.backend.repository.CartRepository;
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
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

   public void placeOrderFromCart(String email) {

    List<Cart> cartItems = cartRepository.findByUserEmail(email);

    for (Cart item : cartItems) {
        Order order = new Order();
        order.setProductId(item.getProductId());
        order.setQuantity(item.getQuantity());
        order.setUserEmail(email);
        order.setOrderDate(LocalDateTime.now());

        orderRepository.save(order);
    }

    // ✅ clear cart after order
    cartRepository.deleteAll(cartItems);
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