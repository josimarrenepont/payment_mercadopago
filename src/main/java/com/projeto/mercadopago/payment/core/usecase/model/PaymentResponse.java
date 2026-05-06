package com.projeto.mercadopago.payment.core.usecase.model;

import com.projeto.mercadopago.payment.core.domain.Payment;
import com.projeto.mercadopago.payment.core.domain.PaymentStatus;

import java.math.BigDecimal;
import java.time.Instant;

public record PaymentResponse(
        Long paymentId,
        Long orderId,
        BigDecimal amount,
        PaymentStatus status,
        Instant createdAt,
        String checkoutUrl
) {
    public static PaymentResponse fromDomain(Payment payment){
        return new PaymentResponse(
                payment.getPaymentId(),
                payment.getOrderId(),
                payment.getAmount(),
                payment.getStatus(),
                payment.getCreatedAt(),
                payment.getMercadoPagoInitPoint()
        );
    }
}
