package com.projeto.mercadopago.order.core.usecase;

import com.projeto.mercadopago.order.core.domain.Order;
import com.projeto.mercadopago.order.core.domain.exception.InvalidOrderOperationException;
import com.projeto.mercadopago.order.core.domain.exception.OrderNotFoundException;
import com.projeto.mercadopago.order.core.port.OrderStoragePort;
import com.projeto.mercadopago.order.core.usecase.model.OrderResponse;

public class RemoveItemFromOrderUseCase {

    private final OrderStoragePort storagePort;

    public RemoveItemFromOrderUseCase(OrderStoragePort storagePort) {
        this.storagePort = storagePort;
    }

    public OrderResponse execute(Long orderId, Long productId) {

        Order order = storagePort.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order with ID " + orderId + " not found"));

        if (order.getStatus() != com.projeto.mercadopago.order.core.domain.OrderStatus.PENDING) {
            throw new InvalidOrderOperationException(
                    "Cannot remove items from order with status: " + order.getStatus()
            );
        }

        try {
            order.removeItem(productId);
        } catch (InvalidOrderOperationException e) {
            throw new InvalidOrderOperationException(
                    "Failed to remove product " + productId + " from order " + orderId + ": " + e.getMessage()
            );
        }

        Order savedOrder = storagePort.save(order);

        return OrderResponse.fromDomain(savedOrder);
    }
}