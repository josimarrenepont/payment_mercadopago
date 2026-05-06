package com.projeto.mercadopago.payment.core.port;

import java.math.BigDecimal;

public interface OrderGateway {
    BigDecimal getOrderTotal(Long orderId);
    boolean orderExists(Long orderId);
}
