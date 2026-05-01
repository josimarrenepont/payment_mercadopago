package com.projeto.mercadopago.order.core.usecase;

import com.projeto.mercadopago.order.core.domain.Order;
import com.projeto.mercadopago.order.core.domain.exception.OrderNotFoundException;
import com.projeto.mercadopago.order.core.port.OrderStoragePort;
import com.projeto.mercadopago.order.core.usecase.model.OrderResponse;

public class FindOrderUseCase {

    private final OrderStoragePort storagePort;

    public FindOrderUseCase(OrderStoragePort storagePort) {
        this.storagePort = storagePort;
    }

    public OrderResponse execute(Long id) {
        Order order = storagePort.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order with ID " + id + " not found"));

        return OrderResponse.fromDomain(order);
    }
}