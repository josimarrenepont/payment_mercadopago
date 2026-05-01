package com.projeto.mercadopago.order.core.usecase;

import com.projeto.mercadopago.order.core.domain.Order;
import com.projeto.mercadopago.order.core.domain.OrderStatus;
import com.projeto.mercadopago.order.core.domain.exception.OrderAlreadyPaidException;
import com.projeto.mercadopago.order.core.domain.exception.OrderNotFoundException;
import com.projeto.mercadopago.order.core.port.OrderStoragePort;

public class UpdateOrderStatusUseCase {

    private final OrderStoragePort storagePort;

    public UpdateOrderStatusUseCase(OrderStoragePort storagePort) {
        this.storagePort = storagePort;
    }

    public void execute(Long orderId, String transactionId) {

        Order order = storagePort.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order with ID " + orderId + " not found"));

        if (order.getStatus() == OrderStatus.PAID) {
            return;
        }

        try {
            order.pay(transactionId);
        } catch (OrderAlreadyPaidException e) {

            return;
        }

        storagePort.save(order);
    }
}