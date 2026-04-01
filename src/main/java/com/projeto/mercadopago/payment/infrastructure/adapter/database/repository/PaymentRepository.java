package com.projeto.mercadopago.payment.infrastructure.adapter.database.repository;

import com.projeto.mercadopago.payment.infrastructure.adapter.database.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
}
