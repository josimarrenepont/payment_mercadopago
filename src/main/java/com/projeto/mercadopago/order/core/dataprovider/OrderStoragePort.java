package com.projeto.mercadopago.order.core.dataprovider;

import com.projeto.mercadopago.order.core.domain.Order;

import java.util.Optional;

public interface OrderStoragePort {

    Order save(Order order);
    Optional<Order> findById(Long id);
}
