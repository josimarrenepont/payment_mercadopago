package com.projeto.mercadopago.payment.core.port;

public interface MercadoPagoGateway {
    String createCheckoutPreference(java.math.BigDecimal amount, String description);
}

