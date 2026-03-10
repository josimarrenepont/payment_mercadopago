package com.projeto.mercadopago.order.dataprovider.database.repository;

import com.projeto.mercadopago.order.dataprovider.database.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
}
