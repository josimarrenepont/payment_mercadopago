package com.projeto.mercadopago.payment.dto;

import java.math.BigDecimal;

public record PaymentRequestDTO (
        Long productId,
        BigDecimal amount
){
}
