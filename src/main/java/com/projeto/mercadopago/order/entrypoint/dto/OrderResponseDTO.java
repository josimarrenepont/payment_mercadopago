package com.projeto.mercadopago.order.entrypoint.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.projeto.mercadopago.order.core.domain.OrderStatus;
import java.math.BigDecimal;
import java.time.Instant;

public record OrderResponseDTO(
        Long id,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
        Instant moment,
        String description,
        BigDecimal total,
        BigDecimal discountAmount,
        OrderStatus status,
        String transactionId,
        BigDecimal discountPercentage) {
}