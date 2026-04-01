package com.projeto.mercadopago.payment.config;

import com.projeto.mercadopago.payment.core.port.PaymentStoragePort;
import com.projeto.mercadopago.payment.core.usecase.CreateCheckoutUseCase;
import com.projeto.mercadopago.payment.core.usecase.FindPaymentUseCase;
import com.projeto.mercadopago.payment.core.port.MercadoPagoGateway;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentUseCaseConfig {

    @Bean
    public CreateCheckoutUseCase
    createCheckoutUseCase(@Qualifier("mercadoPagoClient") MercadoPagoGateway gateway, PaymentStoragePort storagePort){
        return new CreateCheckoutUseCase(gateway, storagePort);
    }
    @Bean
    public FindPaymentUseCase findPaymentUseCase(PaymentStoragePort storagePort){
        return new FindPaymentUseCase(storagePort);
    }
}

