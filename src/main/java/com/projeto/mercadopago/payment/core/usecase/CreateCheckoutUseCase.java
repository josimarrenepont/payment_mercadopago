package com.projeto.mercadopago.payment.core.usecase;

import com.projeto.mercadopago.payment.core.port.PaymentStoragePort;
import com.projeto.mercadopago.payment.core.domain.Payment;
import com.projeto.mercadopago.payment.core.port.MercadoPagoGateway;

public class CreateCheckoutUseCase {

    private final MercadoPagoGateway gateway;
    private final PaymentStoragePort storagePort;

    public CreateCheckoutUseCase(MercadoPagoGateway gateway, PaymentStoragePort storagePort) {
        this.gateway = gateway;
        this.storagePort = storagePort;
    }

    public String execute(Payment payment) {
        String initPoint = gateway.createCheckoutPreference();
        storagePort.save(payment);
        return initPoint;
    }
}
