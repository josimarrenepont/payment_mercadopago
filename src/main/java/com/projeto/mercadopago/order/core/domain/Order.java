package com.projeto.mercadopago.order.core.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Order {

    private final Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private final Instant moment;
    private String transactionId;
    private String description;

    private OrderStatus status;
    private final Set<OrderItem> items = new HashSet<>();

    public Order(Long id, Instant moment, String transactionId, String description, OrderStatus status) {
        this.id = id;
        this.moment = moment;
        this.transactionId = transactionId;
        this.description = description;
        this.status = status;
    }

    public void pay(String transactionId){
        this.transactionId = transactionId;
        this.status = OrderStatus.PAID;
    }

    public BigDecimal getTotal(){
        return items.stream()
                .map(OrderItem::getSubTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
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

    public OrderStatus getStatus() {
        return status;
    }

    public void addItem(OrderItem item){
        this.items.add(item);
    }

    public Set<OrderItem> getItems(){
        return java.util.Collections.unmodifiableSet(items);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Order order)) return false;
        return Objects.equals(getId(), order.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
