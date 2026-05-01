package com.projeto.mercadopago.order.core.usecase.model;

import com.projeto.mercadopago.order.core.domain.Order;
import com.projeto.mercadopago.order.core.domain.OrderStatus;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record OrderResponse(
        Long id,
        Instant moment,
        String description,
        BigDecimal total,
        BigDecimal discountAmount,
        OrderStatus status,
        String transactionId,
        BigDecimal discountPercentage,
        List<OrderItemResponse> items
) {
    public static OrderResponse fromDomain(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getMoment(),
                order.getDescription(),
                order.getTotal(),
                order.getDiscountAmount(),
                order.getStatus(),
                order.getTransactionId(),
                order.getDiscountPercentage(),
                order.getItems().stream()
                        .map(OrderItemResponse::fromDomain)
                        .toList()
        );
    }
}