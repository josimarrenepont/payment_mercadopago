package com.projeto.mercadopago.order.dataprovider.database.entity;

import com.projeto.mercadopago.order.core.domain.Order;
import com.projeto.mercadopago.order.core.domain.OrderStatus;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Instant moment;
    private String transactionId;
    private String description;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "order_id")
    private Set<OrderItemEntity> items;

    public static OrderEntity fromDomain(Order order){
        OrderEntity entity = new OrderEntity();
        entity.id = order.getId();
        entity.moment = order.getMoment();
        entity.transactionId = order.getTransactionId();
        entity.description = order.getDescription();
        entity.status = order.getStatus();

        entity.items = order.getItems().stream().
                map(OrderItemEntity::fromDomain).collect(Collectors.toSet());

        return entity;
    }
    public Order toDomain(){
        Order order = new Order(
                this.id,
                this.moment,
                this.transactionId,
                this.description,
                this.status
                );

        if(this.items != null){
            this.items.forEach(itemEntity -> order.addItem(itemEntity.toDomain()));
        }

        return order;
    }
}
