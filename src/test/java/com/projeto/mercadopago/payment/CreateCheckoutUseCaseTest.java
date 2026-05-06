package com.projeto.mercadopago.payment;

import com.projeto.mercadopago.payment.core.domain.PaymentStatus;
import com.projeto.mercadopago.payment.core.port.MercadoPagoGateway;
import com.projeto.mercadopago.payment.core.port.PaymentStoragePort;
import com.projeto.mercadopago.payment.core.domain.Payment;
import com.projeto.mercadopago.payment.core.usecase.CreatePaymentUseCase;
import com.projeto.mercadopago.payment.core.usecase.model.CreatePaymentCommand;
import com.projeto.mercadopago.payment.core.usecase.model.PaymentResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CreateCheckoutUseCaseTest {
    private MercadoPagoGateway gateway;
    private PaymentStoragePort storagePort;
    private CreatePaymentUseCase useCase;

    @BeforeEach
    void setUp(){

        gateway = mock(MercadoPagoGateway.class);
        storagePort = mock(PaymentStoragePort.class);

        useCase = new CreatePaymentUseCase(
                gateway, storagePort
        );
    }
    @Test
    void shouldSuccessfullyCreateCheckout(){
        Long orderId = 1L;
        BigDecimal amount = new BigDecimal("100.00");
        String description = "Order #1";
        String mockInitPoint = "https://link-mercado-pago.com";

        CreatePaymentCommand command = new CreatePaymentCommand(orderId, amount, description);

        when(gateway.createCheckoutPreference(any(BigDecimal.class), anyString()))
                .thenReturn(mockInitPoint);

        PaymentResponse response = useCase.execute(command);

        assertNotNull(response);

        assertEquals(orderId, response.orderId());
        assertEquals(amount, response.amount());
        assertEquals(PaymentStatus.PENDING, response.status());

        assertNotNull(response.createdAt());
        assertEquals(mockInitPoint, response.checkoutUrl());

        verify(gateway).createCheckoutPreference(eq(amount), eq(description));
        verify(storagePort, times(1)).save(any(Payment.class));
    }

    @Test
    void shouldThrowExceptionWhenGatewayFails() {

        CreatePaymentCommand command = new CreatePaymentCommand(1L, new BigDecimal("100.00"), "Order #1");

        when(gateway.createCheckoutPreference(any(BigDecimal.class), anyString()))
                .thenThrow(new RuntimeException("Mercado Pago error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            useCase.execute(command);
        });

        assertEquals("Mercado Pago error", exception.getMessage());

        verify(storagePort, never()).save(any(Payment.class));
    }

    @Test
    void shouldSavePaymentWithCorrectData() {

        Long orderId = 1L;
        BigDecimal amount = new BigDecimal("100.00");
        String description = "Order #1";
        String mockInitPoint = "https://link-mercado-pago.com";

        CreatePaymentCommand command = new CreatePaymentCommand(orderId, amount, description);


        when(gateway.createCheckoutPreference(any(BigDecimal.class), anyString()))
                .thenReturn(mockInitPoint);


        doAnswer(invocation -> {
            Payment savedPayment = invocation.getArgument(0);
            assertNotNull(savedPayment);
            assertEquals(orderId, savedPayment.getOrderId());
            assertEquals(amount, savedPayment.getAmount());
            assertEquals(PaymentStatus.PENDING, savedPayment.getStatus());
            assertNotNull(savedPayment.getCreatedAt());
            return null;
        }).when(storagePort).save(any(Payment.class));

        useCase.execute(command);

        verify(storagePort, times(1)).save(any(Payment.class));
    }
}
