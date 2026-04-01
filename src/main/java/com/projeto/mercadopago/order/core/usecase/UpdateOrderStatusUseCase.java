package com.projeto.mercadopago.order.core.usecase;

import com.projeto.mercadopago.order.core.port.OrderStoragePort;
import com.projeto.mercadopago.order.core.domain.Order;
import com.projeto.mercadopago.order.core.domain.OrderStatus;
import org.apache.velocity.exception.ResourceNotFoundException;

public class UpdateOrderStatusUseCase {

    private final OrderStoragePort storagePort;

    public UpdateOrderStatusUseCase(OrderStoragePort storagePort) {
        this.storagePort = storagePort;
    }
    public void execute(Long orderId, String transactionId){
        Order order = storagePort.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        if(order.getStatus() == OrderStatus.PAID){
            return;
        }

        order.pay(transactionId);

        storagePort.save(order);
    }
}
