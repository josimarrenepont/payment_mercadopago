package com.projeto.mercadopago.order.core.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Order {

    private final Long id;

    private final Instant moment;
    private String transactionId;
    private String description;

    private OrderStatus status;
    private final Set<OrderItem> items = new HashSet<>();

    private BigDecimal total;

    public Order(Long id, Instant moment, String transactionId, String description, OrderStatus status) {
        this.id = id;
        this.moment = moment;
        this.transactionId = transactionId;
        this.description = description;
        this.status = status;
        this.total = BigDecimal.ZERO;
    }

    public void pay(String transactionId){
        if(this.status != OrderStatus.PENDING){
            throw new IllegalArgumentException("Order cannot be paid in current status: " + this.status);
        }
        if(transactionId == null || transactionId.isBlank()){
            throw new IllegalArgumentException("Invalid trasanction ID");
        }
        this.transactionId = transactionId;
        this.status = OrderStatus.PAID;
    }

    public void loadItem(OrderItem item){
        this.items.add(item);
        this.total = calculateTotal();
    }

    public void addItem(OrderItem item){
        if(status != OrderStatus.PENDING){
            throw new IllegalArgumentException("It is not possible to change items of an order that is not pending");
        }

        items.stream()
                        .filter(i -> i.getProductId().equals(item.getProductId()))
                .findFirst()
                .ifPresentOrElse(
                        existingItem -> existingItem.addQuantity(item.getQuantity()),
                        () -> this.items.add(item)
                );
        this.total = calculateTotal();
    }

    public BigDecimal calculateTotal(){
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

    public BigDecimal getTotal() {
        return total;
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
