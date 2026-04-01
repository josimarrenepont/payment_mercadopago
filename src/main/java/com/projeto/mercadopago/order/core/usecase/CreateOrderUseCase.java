package com.projeto.mercadopago.order.core.usecase;

import com.projeto.mercadopago.order.core.port.OrderStoragePort;
import com.projeto.mercadopago.order.core.domain.Order;

public class CreateOrderUseCase {
    private final OrderStoragePort storagePort;

    public CreateOrderUseCase(OrderStoragePort storagePort) {
        this.storagePort = storagePort;
    }

    public Order execute(Order order){
        if(order.getItems().isEmpty()){
            throw new IllegalArgumentException("Cannot create an order without itemss");
        }

        return storagePort.save(order);
    }
}
