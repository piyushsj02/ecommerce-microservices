package com.ecommerce.backend.dto;

public class CartResponse {
    private Long id;
    private Long productId;
    private int quantity;
    private String productName;
    private double price;

    public CartResponse(Long id, Long productId, int quantity, String productName, double price) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.productName = productName;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getProductName() {
        return productName;
    }

    public double getPrice() {
        return price;
    }

    // getters
    
}