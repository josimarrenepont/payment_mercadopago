package com.projeto.mercadopago.order.entrypoint.dto;

import java.util.List;

public record OrderRequestDTO(
        String description,
        List<OrderItemRequestDTO> items
) {}