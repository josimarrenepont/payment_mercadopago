package com.projeto.mercadopago.payment.config;

import com.projeto.mercadopago.payment.core.dataprovider.PaymentStoragePort;
import com.projeto.mercadopago.payment.core.usecase.CreateCheckoutUseCase;
import com.projeto.mercadopago.payment.core.usecase.FindPaymentUseCase;
import com.projeto.mercadopago.payment.gateway.MercadoPagoGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public CreateCheckoutUseCase
    createCheckoutUseCase(MercadoPagoGateway gateway, PaymentStoragePort storagePort){
        return new CreateCheckoutUseCase(gateway, storagePort);
    }
    @Bean
    public FindPaymentUseCase findPaymentUseCase(PaymentStoragePort storagePort){
        return new FindPaymentUseCase(storagePort);
    }
}

