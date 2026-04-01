package com.projeto.mercadopago.payment.core.port;

import com.projeto.mercadopago.payment.core.domain.Payment;

import java.util.Optional;

public interface PaymentStoragePort {
    void save(Payment payment);

    Optional<Payment> findById(Long id);
}
