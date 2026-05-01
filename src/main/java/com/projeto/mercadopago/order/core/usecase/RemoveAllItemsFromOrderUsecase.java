package com.projeto.mercadopago.order.core.usecase;

import com.projeto.mercadopago.order.core.domain.Order;
import com.projeto.mercadopago.order.core.domain.exception.OrderNotFoundException;
import com.projeto.mercadopago.order.core.port.OrderStoragePort;
import com.projeto.mercadopago.order.core.usecase.model.OrderResponse;

public class RemoveAllItemsFromOrderUsecase {

    private final OrderStoragePort storagePort;

    public RemoveAllItemsFromOrderUsecase(OrderStoragePort storagePort) {
        this.storagePort = storagePort;
    }

    public OrderResponse execute(Long orderId) {
        Order order = storagePort.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not Found"));

        order.clearAllItems();

        Order savedOrder = storagePort.save(order);

        return OrderResponse.fromDomain(savedOrder);
    }
}