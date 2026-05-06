package com.projeto.mercadopago.payment.core.usecase.model;

import java.math.BigDecimal;

public record CreatePaymentCommand(
        Long orderId,
        BigDecimal amount,
        String description
) {
    public CreatePaymentCommand{
        if(orderId == null || orderId <= 0){
            throw new IllegalArgumentException("Order ID n=is required");
        }
        if(amount == null && amount.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Amount must be positive");
        }
        if(description == null || description.isBlank()){
            throw new IllegalArgumentException("Description is required");
        }
    }
}
