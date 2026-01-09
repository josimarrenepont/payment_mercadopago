package com.projeto.mercadopago.payment.entity;

import com.projeto.mercadopago.payment.domain.Payment;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "payments")
public class PaymentEntity {

    @Id
    private Long id;
    private String status;
    private BigDecimal transactionAmount;

    public static PaymentEntity fromDomain(Payment payment){
        PaymentEntity entity = new PaymentEntity();

        entity.id = payment.getId();
        entity.status = payment.getStatus();
        entity.transactionAmount = payment.getTransactionAmount();

        return entity;
    }
}
