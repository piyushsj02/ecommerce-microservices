package com.ecommerce.backend.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.backend.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findByUserEmail(String email);

    Optional<Cart> findByUserEmailAndProductId(String email, Long productId);
    
}
