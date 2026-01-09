package com.projeto.mercadopago.payment.service;

import com.projeto.mercadopago.payment.domain.Payment;

public interface PagamentoService {
    Payment findById(String paymentId);
}
