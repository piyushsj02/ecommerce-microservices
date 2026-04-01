package com.ecommerce.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.backend.entity.Cart;
import com.ecommerce.backend.service.CartService;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/{productId}")
    public Cart addToCart(@PathVariable Long productId) {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return cartService.addToCart(email, productId);
    }

    @GetMapping
    public List<Cart> getCart() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return cartService.getCart(email);
    }

    @DeleteMapping("/{id}")
    public void removeFromCart(@PathVariable Long id) {
        cartService.removeFromCart(id);
    }
}