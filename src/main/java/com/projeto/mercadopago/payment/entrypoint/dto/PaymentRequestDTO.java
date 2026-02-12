package com.projeto.mercadopago.payment.entrypoint.dto;

import java.math.BigDecimal;

public record PaymentRequestDTO (
        Long productId,
        BigDecimal amount
){
}
