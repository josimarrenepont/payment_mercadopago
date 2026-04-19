package com.projeto.mercadopago.order.infrastructure.adapter.database.repository;

import com.projeto.mercadopago.order.infrastructure.adapter.database.entity.CouponEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CouponJpaRepository extends JpaRepository<CouponEntity, Long> {

    Optional<CouponEntity> findByCodeAndActiveTrueAndExpirationDateAfter(String code, java.time.Instant now);
}
