package com.ecommerce.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.backend.dto.CartResponse;
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
public List<CartResponse> getCart(Authentication auth) {
    return cartService.getCart(auth.getName());
}

    @PutMapping("/update")
    public Cart updateQuantity(@RequestParam Long productId,
            @RequestParam int quantity) {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return cartService.updateQuantity(email, productId, quantity);
    }

    @DeleteMapping("/product/{productId}")
    public void removeByProductId(@PathVariable Long productId) {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        cartService.removeByProductId(email, productId);
    }
}