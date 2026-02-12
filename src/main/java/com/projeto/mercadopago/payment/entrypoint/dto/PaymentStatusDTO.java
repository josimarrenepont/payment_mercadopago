package com.projeto.mercadopago.payment.entrypoint.dto;

import java.math.BigDecimal;
import java.time.Instant;

public record PaymentStatusDTO(
        Long externalId,
        String status,
        BigDecimal transactionAmount,
        String currency,
        Instant dateCreated
) {
}
