package com.projeto.mercadopago.payment.gateway;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class MockMercadoPagoClient implements MercadoPagoGateway {

    @Override
    public String createCheckoutPreference() {
        // Retorna init_point fake para dev/portfólio
        return "https://sandbox.mercadopago.com.br/checkout/v1/redirect?pref_id=MOCK";
    }
}
