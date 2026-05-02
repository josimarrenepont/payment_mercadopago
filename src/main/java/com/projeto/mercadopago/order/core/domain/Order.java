package com.projeto.mercadopago.order.core.domain;

import com.projeto.mercadopago.order.core.domain.exception.InvalidOrderOperationException;
import com.projeto.mercadopago.order.core.domain.exception.OrderAlreadyPaidException;


import java.math.BigDecimal;
import java.math.RoundingMode;
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

    private BigDecimal discountPercentage = BigDecimal.ZERO;

    public Order(String description){
        this.id = null;
        this.moment = Instant.now();
        this.status = OrderStatus.PENDING;
        this.description = description;
    }

    public Order(Long id, Instant moment, String transactionId, String description, OrderStatus status) {
        this.id = id;
        this.moment = moment;
        this.transactionId = transactionId;
        this.description = description;
        this.status = status;
    }

    public void pay(String transactionId){
        if(this.status != OrderStatus.PENDING){
            throw new OrderAlreadyPaidException("Order cannot be paid in current status: " + this.status);
        }
        if(transactionId == null || transactionId.isBlank()){
            throw new InvalidOrderOperationException("Transaction ID provided by Mercado Pago is invalid or empty.");
        }
        this.transactionId = transactionId;
        this.status = OrderStatus.PAID;
    }

    public BigDecimal getTotal() {
        BigDecimal total = calculateTotal();
        BigDecimal discountValue = total.multiply(discountPercentage).setScale(2, RoundingMode.HALF_UP);
        return total.subtract(discountValue);
    }

    public void loadItem(OrderItem item){
        this.items.add(item);
    }

    public void addItem(OrderItem item){
        if(status != OrderStatus.PENDING){
            throw new InvalidOrderOperationException("It is not possible to change items of an order that is not pending");
        }

        items.stream()
                .filter(i -> i.getProductId().equals(item.getProductId()))
                .findFirst()
                .ifPresentOrElse(
                        existingItem -> existingItem.addQuantity(item.getQuantity()),
                        () -> this.items.add(item)
                );
    }

    public void applyDiscount(BigDecimal discountPercentage){
        if(discountPercentage == null || discountPercentage.compareTo(BigDecimal.ZERO) <= 0){
            throw new InvalidOrderOperationException("Invalid discount value");
        }
        if(discountPercentage.compareTo(new BigDecimal("0.80")) > 0){
            throw new InvalidOrderOperationException("Discount exceeds maximum limit");
        }

        this.discountPercentage = discountPercentage;
    }

    public BigDecimal calculateTotal(){
        return items.stream()
                .map(OrderItem::getSubTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getDiscountAmount() {
        return calculateTotal().multiply(discountPercentage)
                .setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountAmountFromDatabase(BigDecimal discountAmount) {
        BigDecimal grossTotal = calculateTotal();
        if(grossTotal.compareTo(BigDecimal.ZERO) > 0 && discountAmount != null){
            this.discountPercentage = discountAmount.divide(grossTotal, 4, RoundingMode.HALF_UP);
        }
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

    public Set<OrderItem> getItems(){
        return java.util.Collections.unmodifiableSet(items);
    }

    public void removeItem(Long productId){
        if(this.status != OrderStatus.PENDING){
            throw new InvalidOrderOperationException("Cannot remove items from a non-pending order");
        }

        boolean removed = items.removeIf(item -> item.getProductId().equals(productId));

        if(!removed){
            throw new InvalidOrderOperationException("Item not found in order");
        }
    }

    public void clearAllItems(){
        if(this.status != OrderStatus.PENDING){
            throw new InvalidOrderOperationException("Cannot clear items from a non-pending order");
        }

        this.items.clear();
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