package com.projeto.mercadopago.order.core.port;

import java.math.BigDecimal;
import java.util.Optional;

public interface CouponStoragePort {
    Optional<BigDecimal> findValidCoupon(String code);
}
