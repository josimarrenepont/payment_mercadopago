package com.projeto.mercadopago.payment.infrastructure.adapter.http;

import com.projeto.mercadopago.payment.core.port.MercadoPagoGateway;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Profile("dev")
public class MockMercadoPagoClient implements MercadoPagoGateway {

    @Override
    public String createCheckoutPreference(BigDecimal amount, String description) {
        // Retorna init_point fake para dev/portfólio
        return "https://sandbox.mercadopago.com.br/checkout/v1/redirect?pref_id=MOCK";
    }
}
