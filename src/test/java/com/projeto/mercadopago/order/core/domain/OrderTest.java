package com.projeto.mercadopago.order.core.domain;

import com.projeto.mercadopago.order.core.domain.exception.InvalidOrderOperationException;
import com.projeto.mercadopago.order.core.domain.exception.OrderAlreadyPaidException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

public class OrderTest {

    private Order createPendingOrder(){
        return new Order(1L, Instant.now(), null, "Test Order", OrderStatus.PENDING);
    }

    @Test
    @DisplayName("It should sum quantities when the same product is added twice")
    public void shouldSumQuantitiesWhenSameProductIsAdded(){
        Order order = createPendingOrder();
        OrderItem item1 = new OrderItem(1L, 100L, new BigDecimal("50.00"), 1);
        OrderItem item2 = new OrderItem(2L, 100L, new BigDecimal("50.00"), 2);

        order.addItem(item1);
        order.addItem(item2);

        assertEquals(1, order.getItems().size());

        OrderItem savedItem = order.getItems().iterator().next();

        assertEquals(3, savedItem.getQuantity());

        assertEquals(new BigDecimal("150.00"), order.getTotal());
    }
    @Test
    @DisplayName("Should throw an exception when trying to add an item to an order that is not pending")
    public void shouldThrowExceptionWhenOrderIsNotPending(){
        Order order = new Order(1L, Instant.now(), "trans-123", "Order", OrderStatus.PAID);

        OrderItem orderItem = new OrderItem(1L, 100L, BigDecimal.TEN, 2);

        assertThrows(InvalidOrderOperationException.class, () -> {
            order.addItem(orderItem);
        });
    }
    @Test
    @DisplayName("You must update the status to PAID and record the transactionId when paying")
    public void shouldUpdateStatusAndTransactionIdWhenPaid(){
        Order order = createPendingOrder();
        String txId = "ABC-123";

        order.pay(txId);

        assertEquals(OrderStatus.PAID, order.getStatus());
        assertEquals(txId, order.getTransactionId());
    }
    @Test
    @DisplayName("You should not allow paying an order that has already been paid")
    public void shouldNotAllowPayingAlreadyPaidOrder(){
        Order order = createPendingOrder();
        order.pay("First-id");

        assertThrows(OrderAlreadyPaidException.class, () ->
        {
           order.pay("Second-id");
        });
    }

    @Test
    @DisplayName("Should throw an exception when trying to pay with an invalid transactionId")
    void shouldThrowExceptionWhenTransactionIdIsInvalid(){
        Order order = createPendingOrder();

        assertThrows(InvalidOrderOperationException.class, () -> order.pay(null));
        assertThrows(InvalidOrderOperationException.class, () -> order.pay(""));
    }

}
