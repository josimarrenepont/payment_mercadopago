package com.projeto.mercadopago.payment.service;


import com.projeto.mercadopago.payment.domain.Payment;
import com.projeto.mercadopago.payment.entity.PaymentEntity;
import com.projeto.mercadopago.payment.gateway.MercadoPagoGateway;
import com.projeto.mercadopago.payment.repository.PaymentRepository;

public class PaymentServiceImpl implements PagamentoService{

    private final PaymentRepository paymentRepository;
    private final MercadoPagoGateway gateway;

    public PaymentServiceImpl(PaymentRepository paymentRepository, MercadoPagoGateway gateway) {
        this.paymentRepository = paymentRepository;
        this.gateway = gateway;
    }

    @Override
    public Payment findById(String paymentId) {
        Payment payment = gateway.findById(paymentId);
        paymentRepository.save(PaymentEntity.fromDomain(payment));

        return payment;
    }
}
