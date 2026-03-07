package com.projeto.mercadopago.order.core.usecase;

import com.projeto.mercadopago.order.core.dataprovider.OrderStoragePort;
import com.projeto.mercadopago.order.core.domain.Order;
import org.apache.velocity.exception.ResourceNotFoundException;

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
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }
}
