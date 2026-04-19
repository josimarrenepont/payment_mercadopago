package com.projeto.mercadopago.order.infrastructure.adapter.database;

import com.projeto.mercadopago.order.core.port.OrderStoragePort;
import com.projeto.mercadopago.order.core.domain.Order;
import com.projeto.mercadopago.order.infrastructure.adapter.database.entity.OrderEntity;
import com.projeto.mercadopago.order.infrastructure.adapter.database.repository.OrderRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OrderStorageAdapter implements OrderStoragePort {

    private final OrderRepository orderRepository;

    public OrderStorageAdapter(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order save(Order order) {
        OrderEntity entity = OrderEntity.fromDomain(order);

        OrderEntity savedEntity = orderRepository.save(entity);

        return savedEntity.toDomain();
    }

    @Override
    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id).map(OrderEntity::toDomain);
    }
}
