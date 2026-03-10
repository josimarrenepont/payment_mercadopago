package com.projeto.mercadopago.order.entrypoint.controller;

import com.projeto.mercadopago.order.core.domain.Order;
import com.projeto.mercadopago.order.core.domain.OrderItem;
import com.projeto.mercadopago.order.core.domain.OrderStatus;
import com.projeto.mercadopago.order.core.usecase.CreateOrderUseCase;
import com.projeto.mercadopago.order.core.usecase.FindOrderUseCase;
import com.projeto.mercadopago.order.core.usecase.UpdateOrderStatusUseCase;
import com.projeto.mercadopago.order.entrypoint.dto.OrderRequestDTO;
import com.projeto.mercadopago.order.entrypoint.dto.OrderResponseDTO;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final CreateOrderUseCase createOrderUseCase;
    private final FindOrderUseCase findOrderUseCase;
    private final UpdateOrderStatusUseCase updateOrderStatusUseCase;

    public OrderController(CreateOrderUseCase createOrderUseCase,
                           FindOrderUseCase findOrderUseCase, UpdateOrderStatusUseCase updateOrderStatusUseCase) {
        this.createOrderUseCase = createOrderUseCase;
        this.findOrderUseCase = findOrderUseCase;
        this.updateOrderStatusUseCase = updateOrderStatusUseCase;
    }

    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody OrderRequestDTO requestDTO){
        Order order = new Order(
                null,
                Instant.now(),
                null,
                requestDTO.description(),
                OrderStatus.PENDING
        );

        requestDTO.items().forEach(item ->
                order.addItem(new OrderItem(
                        item.productId(),
                        item.price(),
                        item.quantity())));

        Order savedOrder = createOrderUseCase.execute(order);

        return ResponseEntity.ok(OrderResponseDTO.fromDomain(savedOrder));
    }
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> findById(@PathVariable Long id){
        Order order = findOrderUseCase.execute(id);

        return ResponseEntity.ok(OrderResponseDTO.fromDomain(order));
    }
}
