package com.projeto.mercadopago.payment.infrastructure.adapter.database.entity;

import com.projeto.mercadopago.payment.core.domain.Payment;
import com.projeto.mercadopago.payment.core.domain.PaymentStatus;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "payments")
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;

    private BigDecimal amount;

    private String status;

    private String mercadoPagoInitPoint;

    private Instant createdAt;

    public PaymentEntity() {}

    public static PaymentEntity fromDomain(Payment payment) {
        PaymentEntity entity = new PaymentEntity();
        entity.id = payment.getPaymentId();
        entity.orderId = payment.getOrderId();
        entity.amount = payment.getAmount();
        entity.status = payment.getStatus().name();
        entity.mercadoPagoInitPoint = payment.getMercadoPagoInitPoint();
        entity.createdAt = payment.getCreatedAt();
        return entity;
    }

    public Payment toDomain() {
        return new Payment(
                this.id,
                this.orderId,
                this.amount,
                PaymentStatus.valueOf(this.status),
                this.createdAt,
                this.mercadoPagoInitPoint
        );
    }


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getMercadoPagoInitPoint() { return mercadoPagoInitPoint; }
    public void setMercadoPagoInitPoint(String mercadoPagoInitPoint) {
        this.mercadoPagoInitPoint = mercadoPagoInitPoint; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}