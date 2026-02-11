package com.projeto.mercadopago.payment.core.usecase;

import com.projeto.mercadopago.payment.core.dataprovider.PaymentStoragePort;
import com.projeto.mercadopago.payment.core.domain.Payment;
import com.projeto.mercadopago.payment.exception.IntegrationException;

public class FindPaymentUseCase {

    private final PaymentStoragePort storagePort;

    public FindPaymentUseCase(PaymentStoragePort storagePort) {
        this.storagePort = storagePort;
    }

    public Payment execute(Long id){
        return storagePort.findById(id)
                .orElseThrow(() -> new IntegrationException("Pagamento não encontrado"));
    }
}
