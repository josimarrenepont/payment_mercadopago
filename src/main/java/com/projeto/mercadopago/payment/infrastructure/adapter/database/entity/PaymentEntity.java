package com.projeto.mercadopago.payment.infrastructure.adapter.database.entity;

import com.projeto.mercadopago.payment.core.domain.Payment;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "payments")
@NoArgsConstructor
public class PaymentEntity {

    @Id
    private Long id;
    private String status;
    private BigDecimal transactionAmount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public static PaymentEntity fromDomain(Payment payment){

        PaymentEntity entity = new PaymentEntity();

        entity.id = payment.getId();
        entity.status = payment.getStatus();
        entity.transactionAmount = payment.getTransactionAmount();

        return entity;
    }
    public Payment toDomain(){
        return new Payment(this.id,
                this.status, this.transactionAmount);
    }
}
