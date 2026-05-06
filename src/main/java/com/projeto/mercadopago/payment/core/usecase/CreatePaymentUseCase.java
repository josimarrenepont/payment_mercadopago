package com.projeto.mercadopago.payment.core.usecase;

import com.projeto.mercadopago.payment.core.domain.Payment;
import com.projeto.mercadopago.payment.core.port.MercadoPagoGateway;
import com.projeto.mercadopago.payment.core.port.PaymentStoragePort;
import com.projeto.mercadopago.payment.core.usecase.model.CreatePaymentCommand;
import com.projeto.mercadopago.payment.core.usecase.model.PaymentResponse;

public class CreatePaymentUseCase {

    private final MercadoPagoGateway gateway;
    private final PaymentStoragePort storagePort;

    public CreatePaymentUseCase(MercadoPagoGateway gateway, PaymentStoragePort storagePort) {
        this.gateway = gateway;
        this.storagePort = storagePort;
    }

    public PaymentResponse execute(CreatePaymentCommand command){
        Payment payment = new Payment(command.orderId(), command.amount());

        String initPoint = gateway.createCheckoutPreference(command.amount(), command.description());

        payment.setMercadoPagoInitPoint(initPoint);

        storagePort.save(payment);

        return PaymentResponse.fromDomain(payment);
    }
}
