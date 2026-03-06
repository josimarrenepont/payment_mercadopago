package com.projeto.mercadopago.order.core.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.Instant;
import java.util.Objects;

public class Order {

    private final Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private final Instant moment;
    private String transactionId;
    private String description;
    private Double total;

    public Order(Long id, Instant moment, String transactionId, String description, Double total) {
        this.id = id;
        this.moment = moment;
        this.transactionId = transactionId;
        this.description = description;
        this.total = total;
    }

    public Long getId() {
        return id;
    }

    public Instant getMoment() {
        return moment;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getDescription() {
        return description;
    }

    public Double getTotal() {
        return total;
    }

    public void updateTransactional(String transactionId){
        this.transactionId = transactionId;
    }
    public void adjustTotal(Double newTotal){
        this.total = newTotal;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Order order)) return false;
        return Objects.equals(getId(), order.getId()) && Objects.equals(getMoment(), order.getMoment()) && Objects.equals(getTransactionId(), order.getTransactionId()) && Objects.equals(getDescription(), order.getDescription()) && Objects.equals(getTotal(), order.getTotal());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getMoment(), getTransactionId(), getDescription(), getTotal());
    }
}
