package com.projeto.mercadopago.order.core.usecase.model;

import java.math.BigDecimal;

public record OrderItemCommand(
        Long productId,
        BigDecimal price,
        Integer quantity
) {
    public OrderItemCommand {
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price must be positive");
        }
    }
}