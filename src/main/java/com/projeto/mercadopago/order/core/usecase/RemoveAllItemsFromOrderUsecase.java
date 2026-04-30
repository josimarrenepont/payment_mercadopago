package com.projeto.mercadopago.order.core.usecase;

import com.projeto.mercadopago.order.core.domain.Order;
import com.projeto.mercadopago.order.core.domain.exception.OrderNotFoundException;
import com.projeto.mercadopago.order.core.port.OrderStoragePort;

public class RemoveAllItemsFromOrderUsecase {

    private final OrderStoragePort storagePort;

    public RemoveAllItemsFromOrderUsecase(OrderStoragePort storagePort) {
        this.storagePort = storagePort;
    }

    public Order execute(Long orderId){

        Order order = storagePort.findById(orderId).orElseThrow(() -> new OrderNotFoundException("Order not Found"));

        order.clearAllItems();

        return storagePort.save(order);
    }
}
