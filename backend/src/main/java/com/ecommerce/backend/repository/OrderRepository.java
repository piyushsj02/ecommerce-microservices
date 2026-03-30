package com.ecommerce.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.backend.entity.Order;

public interface OrderRepository extends JpaRepository<Order,Long> {

    List<Order> findByUserEmail(String email);
    
}
