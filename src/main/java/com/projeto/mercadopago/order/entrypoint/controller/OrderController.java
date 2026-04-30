package com.projeto.mercadopago.order.entrypoint.controller;

import com.projeto.mercadopago.order.core.domain.Order;
import com.projeto.mercadopago.order.core.usecase.*;
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

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    private final CreateOrderUseCase createOrderUseCase;
    private final FindOrderUseCase findOrderUseCase;
    private final UpdateOrderStatusUseCase updateOrderStatusUseCase;
    private final RemoveItemFromOrderUseCase removeItemFromOrderUseCase;
    private final RemoveAllItemsFromOrderUsecase removeAllItemsFromOrderUsecase;

    public OrderController(CreateOrderUseCase createOrderUseCase,
                           FindOrderUseCase findOrderUseCase, UpdateOrderStatusUseCase updateOrderStatusUseCase, RemoveItemFromOrderUseCase removeItemFromOrderUseCase, RemoveAllItemsFromOrderUsecase removeAllItemsFromOrderUsecase) {
        this.createOrderUseCase = createOrderUseCase;
        this.findOrderUseCase = findOrderUseCase;
        this.updateOrderStatusUseCase = updateOrderStatusUseCase;
        this.removeItemFromOrderUseCase = removeItemFromOrderUseCase;
        this.removeAllItemsFromOrderUsecase = removeAllItemsFromOrderUsecase;
    }

    @PostMapping
    @Operation(summary = "Create checkout", description = "Creates a checkout session for a order.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Checkout created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody OrderRequestDTO requestDTO) {

        Order order = new Order(requestDTO.description());

        order.addItemsFromRequest(requestDTO.items());

        Order savedOrder = createOrderUseCase.execute(order, requestDTO.couponCode());

        return ResponseEntity.ok(OrderResponseDTO.fromDomain(savedOrder));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Search Order for ID", description = "Returns the orders details based on the provided ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order successfully found"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    public ResponseEntity<OrderResponseDTO> findById(@PathVariable Long id) {
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
                                                   @RequestParam(name = "data_id") String dataId) {
        log.info("Receiving payment notification for order ID: {} with Data ID: {}", id, dataId);

        updateOrderStatusUseCase.execute(id, dataId);

        log.info("Order status updated successfully for order: {}", id);
        return ResponseEntity.noContent().build();

    }

    @DeleteMapping("/{orderId}/items/{productId}")
    @Operation(summary = "Remove a specific item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item removed successfully"),
            @ApiResponse(responseCode = "404", description = "Order or Item not found"),
            @ApiResponse(responseCode = "400", description = "Order is not in PENDING status")
    })
    public ResponseEntity<OrderResponseDTO> removeItem(@PathVariable Long orderId, @PathVariable Long productId){

        log.info("Request to remove item {} from order {}", productId, orderId);

        Order updatedOrder = removeItemFromOrderUseCase.execute(orderId, productId);

        log.info("Item {} removed. Order {} total updated to: {}", productId, orderId, updatedOrder.getTotal());
        return ResponseEntity.ok(OrderResponseDTO.fromDomain(updatedOrder));
    }

    @DeleteMapping("/{orderId}/items")
    @Operation(summary = "Remove all items (clear cart)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All items removed successfully"),
            @ApiResponse(responseCode = "404", description = "Order not found"),
            @ApiResponse(responseCode = "400", description = "Order is not in PENDING status")
    })
    public ResponseEntity<OrderResponseDTO> clearAllItems(@PathVariable Long orderId){

        log.info("Request to clear all items from order {}", orderId);

        Order updatedOrder = removeAllItemsFromOrderUsecase.execute(orderId);

        log.info("Order {} is now empty. Total: {}", orderId, updatedOrder.getTotal());
        return ResponseEntity.ok(OrderResponseDTO.fromDomain(updatedOrder));
    }
}