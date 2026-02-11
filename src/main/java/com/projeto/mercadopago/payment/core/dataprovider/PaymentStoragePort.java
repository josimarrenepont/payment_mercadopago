package com.projeto.mercadopago.payment.core.dataprovider;

import com.projeto.mercadopago.payment.core.domain.Payment;

import java.util.Optional;

public interface PaymentStoragePort {
    void save(Payment payment);

    Optional<Payment> findById(Long id);
}
