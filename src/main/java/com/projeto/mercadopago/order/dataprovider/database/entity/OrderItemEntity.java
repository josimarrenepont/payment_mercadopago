package com.projeto.mercadopago.order.dataprovider.database.entity;

import com.projeto.mercadopago.order.core.domain.OrderItem;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
public class OrderItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal price;
    private Integer quantity;

    public OrderItemEntity() {
    }

    public OrderItemEntity(Long id, BigDecimal price, Integer quantity) {
        this.id = id;
        this.price = price;
        this.quantity = quantity;
    }

    public static OrderItemEntity fromDomain(OrderItem item){
        OrderItemEntity itemEntity = new OrderItemEntity();
        itemEntity.id = item.getId();
        itemEntity.price = item.getPrice();
        itemEntity.quantity = item.getQuantity();

        return itemEntity;
    }

    public OrderItem toDomain(){
        return new OrderItem(
                this.id,
                this.price,
                this.quantity
        );
    }
}
