package com.projeto.mercadopago.payment.dataprovider.database;

import com.projeto.mercadopago.payment.core.domain.Payment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
public class PaymentStorageAdapterTest {

    @Autowired
    private PaymentStorageAdapter storageAdapter;

    @Test
    void shouldSaveAndFindPayment(){
        Payment payment = new Payment(1L, "TEST", new BigDecimal("100.00"));
        storageAdapter.save(payment);

        Optional<Payment> result = storageAdapter.findById(1L);

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals("TEST", result.get().getStatus());
    }
}
