package com.projeto.mercadopago.payment.repository;

import com.projeto.mercadopago.payment.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {

}
