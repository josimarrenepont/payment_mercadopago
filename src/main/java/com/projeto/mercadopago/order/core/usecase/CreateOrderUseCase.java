package com.projeto.mercadopago.order.core.usecase;

import com.projeto.mercadopago.order.core.domain.exception.InvalidOrderOperationException;
import com.projeto.mercadopago.order.core.port.OrderStoragePort;
import com.projeto.mercadopago.order.core.domain.Order;

public class CreateOrderUseCase {
    private final OrderStoragePort storagePort;

    public CreateOrderUseCase(OrderStoragePort storagePort) {
        this.storagePort = storagePort;
    }

    public Order execute(Order order){
        if(order.getItems().isEmpty()){
            throw new InvalidOrderOperationException("Cannot create an order without items");
        }

        return storagePort.save(order);
    }
}
