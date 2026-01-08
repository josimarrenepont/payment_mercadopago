package com.projeto.mercadopago.payment.gateway;

import com.projeto.mercadopago.payment.domain.Payment;

public interface MercadoPagoGateway {
    Payment findById(String paymentId);
}
