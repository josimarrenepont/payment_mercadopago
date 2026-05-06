package com.projeto.mercadopago.payment;

import com.projeto.mercadopago.payment.core.port.PaymentStoragePort;
import com.projeto.mercadopago.payment.core.domain.Payment;
import com.projeto.mercadopago.payment.core.domain.PaymentStatus;
import com.projeto.mercadopago.payment.core.usecase.FindPaymentUseCase;
import com.projeto.mercadopago.payment.core.usecase.model.PaymentResponse;
import com.projeto.mercadopago.common.exception.IntegrationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FindPaymentUseCaseTest {

    private PaymentStoragePort storagePort;
    private FindPaymentUseCase useCase;

    @BeforeEach
    void setUp() {
        storagePort = mock(PaymentStoragePort.class);
        useCase = new FindPaymentUseCase(storagePort);
    }

    @Test
    void shouldReturnPaymentResponseWhenIdExists() {

        Long paymentId = 1L;
        Long orderId = 100L;
        BigDecimal amount = new BigDecimal("27.51");
        PaymentStatus status = PaymentStatus.PENDING;
        Instant createdAt = Instant.now();
        String mercadoPagoInitPoint = "https://checkout.mercadopago.com/123";

        Payment payment = new Payment(
                paymentId,
                orderId,
                amount,
                status,
                createdAt,
                mercadoPagoInitPoint
        );

        when(storagePort.findById(paymentId)).thenReturn(Optional.of(payment));

        PaymentResponse response = useCase.execute(paymentId);

        assertNotNull(response);
        assertEquals(paymentId, response.paymentId());
        assertEquals(orderId, response.orderId());
        assertEquals(amount, response.amount());
        assertEquals(status, response.status());
        assertEquals(createdAt, response.createdAt());
        assertEquals(mercadoPagoInitPoint, response.checkoutUrl());
    }

    @Test
    void shouldThrowExceptionWhenPaymentNotFound() {

        when(storagePort.findById(999L)).thenReturn(Optional.empty());

        IntegrationException exception = assertThrows(
                IntegrationException.class,
                () -> useCase.execute(999L)
        );

        assertEquals("Payment not found", exception.getMessage());
    }
}