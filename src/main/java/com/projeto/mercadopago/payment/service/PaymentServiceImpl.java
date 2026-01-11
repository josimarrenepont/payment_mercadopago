package com.projeto.mercadopago.payment.service;

import com.projeto.mercadopago.payment.gateway.MercadoPagoGateway;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final MercadoPagoGateway gateway;

    public PaymentServiceImpl(MercadoPagoGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public String createCheckoutPreference() {
        return gateway.createCheckoutPreference();
    }
}
