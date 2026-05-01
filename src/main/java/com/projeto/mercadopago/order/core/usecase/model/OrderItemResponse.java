package com.projeto.mercadopago.order.core.usecase.model;

import com.projeto.mercadopago.order.core.domain.OrderItem;
import java.math.BigDecimal;

public record OrderItemResponse(
        Long productId,
        BigDecimal price,
        Integer quantity,
        BigDecimal subTotal
) {
    public static OrderItemResponse fromDomain(OrderItem item) {
        return new OrderItemResponse(
                item.getProductId(),
                item.getPrice(),
                item.getQuantity(),
                item.getSubTotal()
        );
    }
}