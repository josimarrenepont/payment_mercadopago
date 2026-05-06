package com.projeto.mercadopago.payment.infrastructure.adapter.database;

import com.projeto.mercadopago.payment.core.domain.Payment;
import com.projeto.mercadopago.payment.core.domain.PaymentStatus;
import com.projeto.mercadopago.payment.infrastructure.adapter.database.entity.PaymentEntity;
import com.projeto.mercadopago.payment.infrastructure.adapter.database.repository.PaymentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class PaymentStorageAdapterTest {

    @Autowired
    private PaymentStorageAdapter storageAdapter;

    @Autowired
    private PaymentRepository paymentRepository;

    @Test
    void shouldSaveAndFindPayment() {

        Long orderId = 100L;
        BigDecimal amount = new BigDecimal("100.00");
        Payment payment = new Payment(orderId, amount);

        storageAdapter.save(payment);

        var allPayments = paymentRepository.findAll();
        Assertions.assertFalse(allPayments.isEmpty());

        Optional<PaymentEntity> foundEntity = allPayments.stream()
                .filter(entity -> entity.getOrderId().equals(orderId))
                .findFirst();

        Assertions.assertTrue(foundEntity.isPresent());

        Payment found = foundEntity.get().toDomain();
        Assertions.assertEquals(orderId, found.getOrderId());
        Assertions.assertEquals(amount, found.getAmount());
        Assertions.assertEquals(PaymentStatus.PENDING, found.getStatus());
        Assertions.assertNotNull(found.getCreatedAt());
    }

    @Test
    void shouldUpdatePaymentStatus() {

        Payment payment = new Payment(200L, new BigDecimal("200.00"));
        storageAdapter.save(payment);

        var allPayments = paymentRepository.findAll();
        PaymentEntity entity = allPayments.stream()
                .filter(e -> e.getOrderId().equals(200L))
                .findFirst()
                .orElseThrow();

        Payment paymentToUpdate = entity.toDomain();
        paymentToUpdate.approved();  // Muda status para APPROVED

        storageAdapter.save(paymentToUpdate);

        PaymentEntity updatedEntity = paymentRepository.findById(entity.getId()).orElseThrow();
        Assertions.assertEquals("APPROVED", updatedEntity.getStatus());
    }
}