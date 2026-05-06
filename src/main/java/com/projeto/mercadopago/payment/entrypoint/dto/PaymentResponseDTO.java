package com.projeto.mercadopago.payment.entrypoint.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.time.Instant;

public record PaymentResponseDTO(
        Long paymentId,
        Long orderId,
        BigDecimal amount,
        String status,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
        Instant createdAt,
        String checkoutUrl
) {}
