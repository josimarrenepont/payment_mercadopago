package com.projeto.mercadopago.order.infrastructure.adapter.database.repository;

import com.projeto.mercadopago.order.infrastructure.adapter.database.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
}
