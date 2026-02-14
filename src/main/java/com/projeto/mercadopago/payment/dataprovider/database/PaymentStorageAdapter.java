// src/main/java/com/projeto/mercadopago/payment/core/dataprovider/database/PaymentStorageAdapter.java
package com.projeto.mercadopago.payment.dataprovider.database;

import com.projeto.mercadopago.payment.core.dataprovider.PaymentStoragePort;
import com.projeto.mercadopago.payment.core.domain.Payment;
import com.projeto.mercadopago.payment.dataprovider.database.entity.PaymentEntity;
import com.projeto.mercadopago.payment.dataprovider.database.repository.PaymentRepository;
import org.springframework.stereotype.Component; // Adicione esta anotação

import java.util.Optional;

@Component
public class PaymentStorageAdapter implements PaymentStoragePort {

    private final PaymentRepository repository;

    public PaymentStorageAdapter(PaymentRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(Payment payment) {
        PaymentEntity entity = PaymentEntity.fromDomain(payment);
        repository.save(entity);
    }

    @Override
    public Optional<Payment> findById(Long id) {
        return repository.findById(id)
                .map(PaymentEntity::toDomain);
    }
}