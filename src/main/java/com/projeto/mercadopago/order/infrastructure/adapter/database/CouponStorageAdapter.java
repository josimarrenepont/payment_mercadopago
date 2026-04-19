package com.projeto.mercadopago.order.infrastructure.adapter.database;

import com.projeto.mercadopago.order.core.port.CouponStoragePort;
import com.projeto.mercadopago.order.infrastructure.adapter.database.entity.CouponEntity;
import com.projeto.mercadopago.order.infrastructure.adapter.database.repository.CouponJpaRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

@Component
public class CouponStorageAdapter implements CouponStoragePort {

    private final CouponJpaRepository couponJpaRepository;

    public CouponStorageAdapter(CouponJpaRepository couponJpaRepository) {
        this.couponJpaRepository = couponJpaRepository;
    }

    @Override
    public Optional<BigDecimal> findValidCoupon(String code) {
        return couponJpaRepository.findByCodeAndActiveTrueAndExpirationDateAfter(code, Instant.now())
                .map(CouponEntity::getPercentage);
    }
}
