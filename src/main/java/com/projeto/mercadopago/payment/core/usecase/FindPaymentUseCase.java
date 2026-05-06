package com.projeto.mercadopago.payment.core.usecase;

import com.projeto.mercadopago.payment.core.port.PaymentStoragePort;
import com.projeto.mercadopago.payment.core.domain.Payment;
import com.projeto.mercadopago.common.exception.IntegrationException;
import com.projeto.mercadopago.payment.core.usecase.model.PaymentResponse;

public class FindPaymentUseCase {

    private final PaymentStoragePort storagePort;

    public FindPaymentUseCase(PaymentStoragePort storagePort) {
        this.storagePort = storagePort;
    }

    public PaymentResponse execute(Long id){
        Payment payment = storagePort.findById(id)
                .orElseThrow(() -> new IntegrationException("Payment not found"));

        return PaymentResponse.fromDomain(payment);
    }
}
