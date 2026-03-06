package com.projeto.mercadopago.order.core.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class OrderItem {
    private final Long id;
    private BigDecimal price;
    private Integer quantity;

    public OrderItem(Long id, BigDecimal price, Integer quantity) {
        this.id = id;
        this.price = price;
        this.quantity = quantity;
    }

    public BigDecimal getSubTotal(){
        return price.multiply(BigDecimal.valueOf(quantity));
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void adjustPrice(BigDecimal newPrice){
        this.price = newPrice;
    }
    public void adjustQuantity(Integer newQauntity){
        this.quantity = newQauntity;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof OrderItem orderItem)) return false;
        return Objects.equals(getId(), orderItem.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
