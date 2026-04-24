package com.projeto.mercadopago.payment;

import com.projeto.mercadopago.payment.core.port.MercadoPagoGateway;
import com.projeto.mercadopago.payment.core.port.PaymentStoragePort;
import com.projeto.mercadopago.payment.core.domain.Payment;
import com.projeto.mercadopago.payment.core.usecase.CreateCheckoutUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CreateCheckoutUseCaseTest {
    private MercadoPagoGateway gateway;
    private PaymentStoragePort storagePort;
    private CreateCheckoutUseCase useCase;

    @BeforeEach
    void setUp(){

        gateway = mock(MercadoPagoGateway.class);
        storagePort = mock(PaymentStoragePort.class);

        useCase = new CreateCheckoutUseCase(
                gateway, storagePort
        );
    }
    @Test
    void shouldSuccessfullyCreateCheckout(){
        BigDecimal amount = new BigDecimal("100.00");
        Payment payment = new Payment(1L, "PENDING", amount);
        when(gateway.createCheckoutPreference(any(BigDecimal.class), anyString()))
                .thenReturn("https://link-mercado-pago.com");

        String link = useCase.execute(payment);

        assertEquals("https://link-mercado-pago.com", link);

        verify(gateway).createCheckoutPreference(eq(amount), anyString());
        verify(storagePort, times(1)).save(payment);
    }

}
