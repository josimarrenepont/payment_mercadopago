package com.projeto.mercadopago.order.infrastructure.config;

import com.projeto.mercadopago.order.core.dataprovider.OrderStoragePort;
import com.projeto.mercadopago.order.core.usecase.CreateOrderUseCase;
import com.projeto.mercadopago.order.core.usecase.FindOrderUseCase;
import com.projeto.mercadopago.order.core.usecase.UpdateOrderStatusUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderUseCaseConfig {

    @Bean
    CreateOrderUseCase createOrderUseCase(OrderStoragePort storagePort){
        return new CreateOrderUseCase(storagePort);
    }
    @Bean
    FindOrderUseCase findOrderUseCase(OrderStoragePort storagePort){
        return new FindOrderUseCase(storagePort);
    }
    @Bean
    UpdateOrderStatusUseCase updateOrderStatusUseCase(OrderStoragePort storagePort){
        return new UpdateOrderStatusUseCase(storagePort);
    }
}
