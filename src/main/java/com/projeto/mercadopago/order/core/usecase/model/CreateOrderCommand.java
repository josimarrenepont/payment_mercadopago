package com.projeto.mercadopago.order.core.usecase.model;

import java.util.List;

public record CreateOrderCommand(
        String description,
        String couponCode,
        List<OrderItemCommand> items
) {
    public CreateOrderCommand {
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Description is required");
        }
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Order must have at least one item");
        }
    }
}