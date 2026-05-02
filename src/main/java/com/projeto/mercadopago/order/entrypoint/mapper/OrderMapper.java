package com.projeto.mercadopago.order.entrypoint.mapper;

import com.projeto.mercadopago.order.core.usecase.model.OrderResponse;
import com.projeto.mercadopago.order.entrypoint.dto.OrderResponseDTO;

public class OrderMapper {

    public static OrderResponseDTO toDTO(OrderResponse response){
        return new OrderResponseDTO(
                response.id(),
                response.moment(),
                response.description(),
                response.total(),
                response.discountAmount(),
                response.status(),
                response.transactionId(),
                response.discountPercentage()
        );
    }
}