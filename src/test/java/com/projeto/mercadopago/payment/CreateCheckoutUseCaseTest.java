package com.projeto.mercadopago.payment;

import com.projeto.mercadopago.payment.core.dataprovider.MercadoPagoGateway;
import com.projeto.mercadopago.payment.core.dataprovider.PaymentStoragePort;
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
        Payment payment = new Payment(1L, "PENDING", new BigDecimal("100.00"));
        when(gateway.createCheckoutPreference()).thenReturn("https://link-mercado-pago.com");

        String link = useCase.execute(payment);

        assertEquals("https://link-mercado-pago.com", link);

        verify(storagePort, times(1)).save(payment);
    }
}
