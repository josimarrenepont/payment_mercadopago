package com.projeto.mercadopago.payment.core.domain;

import java.math.BigDecimal;

public class Payment {
    private Long id;
    private String status;
    private BigDecimal transactionAmount;

    public Payment(Long id, String status, BigDecimal transactionAmount) {
        this.id = id;
        this.status = status;
        this.transactionAmount = transactionAmount;
    }

    public Long getId() { return id; }
    public String getStatus() { return status; }
    public BigDecimal getTransactionAmount() { return transactionAmount; }
}
