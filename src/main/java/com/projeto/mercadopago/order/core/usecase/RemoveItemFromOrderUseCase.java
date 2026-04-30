package com.projeto.mercadopago.order.core.usecase;

import com.projeto.mercadopago.order.core.domain.Order;
import com.projeto.mercadopago.order.core.domain.exception.OrderNotFoundException;
import com.projeto.mercadopago.order.core.port.OrderStoragePort;

public class RemoveItemFromOrderUseCase {

    private final OrderStoragePort storagePort;

    public RemoveItemFromOrderUseCase(OrderStoragePort storagePort) {
        this.storagePort = storagePort;
    }

    public Order execute(Long orderId, Long productId){

        Order order = storagePort.findById(orderId).orElseThrow(() -> new OrderNotFoundException("Order not Found"));

        order.removeItem(productId);

        return storagePort.save(order);
    }
}
