package com.ecommerce.backend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.backend.dto.CartResponse;
import com.ecommerce.backend.entity.Cart;
import com.ecommerce.backend.entity.Product;
import com.ecommerce.backend.repository.CartRepository;
import com.ecommerce.backend.repository.ProductRepository;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired ProductRepository productRepository;

    public Cart addToCart(String email, Long productId) {

        Optional<Cart> existing = cartRepository.findByUserEmailAndProductId(email, productId);

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
public List<CartResponse> getCart(String email) {

    List<Cart> cartItems = cartRepository.findByUserEmail(email);

    return cartItems.stream().map(item -> {

        Product product = productRepository
                .findById(item.getProductId())
                .orElseThrow();

        return new CartResponse(
                item.getId(),
                item.getProductId(),
                item.getQuantity(),
                product.getName(),
                product.getPrice()
        );

    }).toList();
}
    public Cart updateQuantity(String email, Long productId, int quantity) {

    Optional<Cart> optionalCart =
            cartRepository.findByUserEmailAndProductId(email, productId);

    if (optionalCart.isEmpty()) {
        throw new RuntimeException("Cart item not found");
    }

    Cart cart = optionalCart.get();

    if (quantity <= 0) {
        cartRepository.delete(cart);
        return null;
    }

    cart.setQuantity(quantity);
    return cartRepository.save(cart);
}

    public void removeByProductId(String email, Long productId) {

        Optional<Cart> optionalCart = cartRepository.findByUserEmailAndProductId(email, productId);

        if (optionalCart.isPresent()) {
            cartRepository.delete(optionalCart.get());
        }
    }
}