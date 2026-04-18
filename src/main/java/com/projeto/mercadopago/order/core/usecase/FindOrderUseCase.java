package com.projeto.mercadopago.order.core.usecase;

import com.projeto.mercadopago.order.core.domain.exception.OrderNotFoundException;
import com.projeto.mercadopago.order.core.port.OrderStoragePort;
import com.projeto.mercadopago.order.core.domain.Order;

public class FindOrderUseCase {

    private final OrderStoragePort storagePort;

    public FindOrderUseCase(OrderStoragePort storagePort) {
        this.storagePort = storagePort;
    }
    public Order execute(Long id){
        if(id == null){
            throw new IllegalArgumentException("Order ID cannot be null");
        }
        return storagePort.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order with ID " + id + " not found"));
    }
}
