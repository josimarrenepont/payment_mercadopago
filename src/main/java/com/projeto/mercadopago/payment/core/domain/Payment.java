package com.projeto.mercadopago.payment.core.domain;

import com.projeto.mercadopago.payment.core.exception.InvalidPaymentOperationException;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

public class Payment {
    private final Long paymentId;
    private final Long orderId;
    private final BigDecimal amount;
    private PaymentStatus status;
    private final Instant createdAt;
    private String mercadoPagoInitPoint;
    private BigDecimal transactionAmount;

    public Payment(Long orderId, BigDecimal amount){
        this.paymentId = null;
        this.orderId = orderId;
        this.amount = amount;
        this.status = PaymentStatus.PENDING;
        this.createdAt = Instant.now();
    }
    public Payment(Long paymentId, Long orderId, BigDecimal amount,
                   PaymentStatus status, Instant createdAt, String mercadoPagoInitPoint) {
        this.paymentId = paymentId;
        this.orderId = orderId;
        this.amount = amount;
        this.status = status;
        this.createdAt = createdAt;
        this.mercadoPagoInitPoint = mercadoPagoInitPoint;
    }

    public void approved(){
        if(this.status != PaymentStatus.PENDING){
            throw new InvalidPaymentOperationException("Only pending payments can be approved");
        }
        this.status = PaymentStatus.APPROVED;
    }

    public void reject(){
        if(this.status != PaymentStatus.PENDING){
            throw new InvalidPaymentOperationException("Only pending payments can be rejected");
        }
        this.status = PaymentStatus.REJECTED;
    }

    public void setMercadoPagoInitPoint(String initPoint){
        if(initPoint == null || initPoint.isBlank()){
            throw new InvalidPaymentOperationException("Invalid Mercado Pago init point");
        }
        this.mercadoPagoInitPoint = initPoint;
    }

    public PaymentStatus isApproved(){
        return this.status = PaymentStatus.APPROVED;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public String getMercadoPagoInitPoint() {
        return mercadoPagoInitPoint;
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Payment payment)) return false;
        return Objects.equals(getPaymentId(), payment.getPaymentId())
                && Objects.equals(getOrderId(), payment.getOrderId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPaymentId(), getOrderId());
    }
}
