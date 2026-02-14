package com.projeto.mercadopago.payment.dataprovider.database.repository;

import com.projeto.mercadopago.payment.dataprovider.database.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
}
