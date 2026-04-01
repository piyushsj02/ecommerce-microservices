package com.ecommerce.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.backend.entity.Cart;
import com.ecommerce.backend.repository.CartRepository;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    public Cart addToCart(String email, Long productId) {

        Optional<Cart> existing =
                cartRepository.findByUserEmailAndProductId(email, productId);

        if (existing.isPresent()) {
            Cart cart = existing.get();
            cart.setQuantity(cart.getQuantity() + 1);
            return cartRepository.save(cart);
        }

        Cart cart = new Cart();
        cart.setProductId(productId);
        cart.setQuantity(1);
        cart.setUserEmail(email);

        return cartRepository.save(cart);
    }

    public List<Cart> getCart(String email) {
        return cartRepository.findByUserEmail(email);
    }

    public void removeFromCart(Long id) {
        cartRepository.deleteById(id);
    }
}