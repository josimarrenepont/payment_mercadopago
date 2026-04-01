package com.projeto.mercadopago.payment;

import com.projeto.mercadopago.payment.core.port.PaymentStoragePort;
import com.projeto.mercadopago.payment.core.domain.Payment;
import com.projeto.mercadopago.payment.core.usecase.FindPaymentUseCase;
import com.projeto.mercadopago.common.exception.IntegrationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FindPaymentUseCaseTest {

    private PaymentStoragePort storagePort;
    private FindPaymentUseCase useCase;

    @BeforeEach
    void setUp(){
        storagePort = mock(PaymentStoragePort.class);
        useCase = new FindPaymentUseCase(storagePort);
    }
    @Test
    void shouldReturnPaymentWhenIdExists(){
        Payment payment = new Payment(1L, "SUCCESS", new BigDecimal("50.00"));
        when(storagePort.findById(1L)).thenReturn(Optional.of(payment));

        Payment result = useCase.execute(1L);

        Assertions.assertEquals("SUCCESS", result.getStatus());
    }
    @Test
    void shouldThrowExceptionWhenPaymentNotFound(){
        when(storagePort.findById(1L)).thenReturn(Optional.empty());
        assertThrows(IntegrationException.class, () ->  useCase.execute(1L));
    }
}
