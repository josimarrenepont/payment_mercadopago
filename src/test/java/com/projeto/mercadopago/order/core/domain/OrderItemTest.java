package com.projeto.mercadopago.order.core.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class OrderItemTest {

    @Test
    @DisplayName("You must calculate the subtotal correctly")
    public void shouldCalculateSubTotalCorrecty() {

        OrderItem orderItem = new OrderItem(1L, 100L, new BigDecimal("50.00"), 2);

        BigDecimal subTotal = orderItem.getSubTotal();

        assertEquals(new BigDecimal("100.00"), subTotal);

    }

    @Test
    @DisplayName("It must sum the quantity correctly")
    void shouldAddQuantity() {
        OrderItem item = new OrderItem(1L, 100L, BigDecimal.TEN, 2);
        item.addQuantity(3);

        assertEquals(5, item.getQuantity());
    }

    @Test
    @DisplayName("Should not allow negative price in adjustment")
    void shouldThrowExceptionWhenPriceIsNegative() {
        OrderItem item = new OrderItem(1L, 100L, BigDecimal.TEN, 1);

        assertThrows(IllegalArgumentException.class, () ->
                item.adjustPrice(new BigDecimal("-1.00"))
        );
    }

    @Test
    @DisplayName("It should not allow a negative quantity in the adjustment")
    void shouldThrowExceptionWhenQuantityIsNegative() {
        OrderItem item = new OrderItem(1L, 100L, BigDecimal.TEN, 1);

        assertThrows(IllegalArgumentException.class, () -> {
            item.adjustQuantity(-5);

        });
    }
    @Test
    @DisplayName("Should successfully adjust the price when it is valid")
    void shouldAdjustPriceWhenValid(){
        OrderItem item = new OrderItem(1L, 100L, BigDecimal.TEN, 1);
        BigDecimal newPrice = new BigDecimal("30.00");

        item.adjustPrice(newPrice);

        assertEquals(newPrice, item.getPrice());
    }

}