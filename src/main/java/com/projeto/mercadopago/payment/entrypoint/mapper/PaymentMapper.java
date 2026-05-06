package com.projeto.mercadopago.payment.entrypoint.mapper;

import com.projeto.mercadopago.payment.core.usecase.model.CreatePaymentCommand;
import com.projeto.mercadopago.payment.core.usecase.model.PaymentResponse;
import com.projeto.mercadopago.payment.entrypoint.dto.PaymentRequestDTO;
import com.projeto.mercadopago.payment.entrypoint.dto.PaymentResponseDTO;

public class PaymentMapper {

    public static CreatePaymentCommand toCommand(PaymentRequestDTO request){
        return new CreatePaymentCommand(
                request.orderId(),
                request.amount(),
                "Order #" + request.orderId()
        );
    }

    public static PaymentResponseDTO toDTO(PaymentResponse response){
        return new PaymentResponseDTO(
                response.paymentId(),
                response.orderId(),
                response.amount(),
                response.status().name(),
                response.createdAt(),
                response.checkoutUrl()
        );
    }
}
