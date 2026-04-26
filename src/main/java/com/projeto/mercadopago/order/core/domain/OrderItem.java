package com.projeto.mercadopago.order.core.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class OrderItem {
    private final Long id;
    private final Long productId;
    private BigDecimal price;
    private Integer quantity;


        public OrderItem(Long id, Long productId, BigDecimal price, Integer quantity) {
        this.id = id;
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
    }

    public BigDecimal getSubTotal(){
        return price.multiply(BigDecimal.valueOf(quantity));
    }

    public Long getId() {
        return id;
    }
    public Long getProductId() {
        return productId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void adjustPrice(BigDecimal newPrice){
        if(newPrice == null || newPrice.compareTo(BigDecimal.ZERO) < 0 ){
            throw new IllegalArgumentException("Price cannot be null or negative");
        }
        this.price = newPrice;
    }
    public void adjustQuantity(Integer newQuantity){
        if(newQuantity == null || newQuantity < 0){
            throw new IllegalArgumentException("Quantity cannot be null or negative");
        }
        this.quantity = newQuantity;
    }

    public void addQuantity(Integer quantityToAdd) {
        this.quantity += quantityToAdd;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof OrderItem orderItem)) return false;
        return Objects.equals(getProductId(), orderItem.getProductId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getProductId());
    }
}
