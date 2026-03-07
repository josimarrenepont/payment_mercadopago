package com.projeto.mercadopago.order.entrypoint.dto;

public record OrderItemRequestDTO(
        Long productId,
        java.math.BigDecimal price,
        Integer quantity
) {
}
