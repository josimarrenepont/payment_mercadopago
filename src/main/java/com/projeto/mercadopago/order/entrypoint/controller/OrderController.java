package com.projeto.mercadopago.order.entrypoint.controller;

import com.projeto.mercadopago.order.core.domain.Order;
import com.projeto.mercadopago.order.core.domain.OrderItem;
import com.projeto.mercadopago.order.core.domain.OrderStatus;
import com.projeto.mercadopago.order.core.usecase.CreateOrderUseCase;
import com.projeto.mercadopago.order.core.usecase.FindOrderUseCase;
import com.projeto.mercadopago.order.core.usecase.UpdateOrderStatusUseCase;
import com.projeto.mercadopago.order.entrypoint.dto.OrderRequestDTO;
import com.projeto.mercadopago.order.entrypoint.dto.OrderResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final static Logger log = (Logger) LoggerFactory.getLogger(OrderController.class);

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
    @Operation(summary = "Create checkout", description = "Creates a checkout session for a order.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Checkout created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
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
                        null,
                        item.productId(),
                        item.price(),
                        item.quantity())));

        Order savedOrder = createOrderUseCase.execute(order);

        return ResponseEntity.ok(OrderResponseDTO.fromDomain(savedOrder));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Search Order for ID", description = "Returns the orders details based on the provided ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order successfully found"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    public ResponseEntity<OrderResponseDTO> findById(@PathVariable Long id){
        Order order = findOrderUseCase.execute(id);

        return ResponseEntity.ok(OrderResponseDTO.fromDomain(order));
    }

    @Operation(
            summary = "Webhook for payment notification",
            description = "Endpoint that Mercado Pago calls to notify that the payment status has changed."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Notification processed successfully"),
            @ApiResponse(responseCode = "404", description = "Order not found in the system"),
            @ApiResponse(responseCode = "400", description = "Invalid notification parameters")
    })
    @PostMapping("/{id}/payment-notification")
    public ResponseEntity<Void> handleNotification(@PathVariable Long id,
                                                   @RequestParam (name = "data_id") String dataId){
        log.info("Receiving payment notification for order ID: {} with Data ID: {}", id, dataId);

        try {
            updateOrderStatusUseCase.execute(id, dataId);
            log.info("Order status updated successfully for order: {}", id);
            return ResponseEntity.noContent().build();
        } catch (Exception e){
            log.error("Error processing notification for order {}: {}", id, e.getMessage());
            throw e;
        }
    }
}
