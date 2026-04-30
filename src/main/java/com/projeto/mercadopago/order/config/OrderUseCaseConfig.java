    package com.projeto.mercadopago.order.config;

    import com.projeto.mercadopago.order.core.port.CouponStoragePort;
    import com.projeto.mercadopago.order.core.port.OrderStoragePort;
    import com.projeto.mercadopago.order.core.usecase.*;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;

    @Configuration
    public class OrderUseCaseConfig {

        @Bean
        CreateOrderUseCase createOrderUseCase(OrderStoragePort storagePort, CouponStoragePort couponStoragePort){
            return new CreateOrderUseCase(storagePort, couponStoragePort);
        }
        @Bean
        FindOrderUseCase findOrderUseCase(OrderStoragePort storagePort){
            return new FindOrderUseCase(storagePort);
        }
        @Bean
        UpdateOrderStatusUseCase updateOrderStatusUseCase(OrderStoragePort storagePort){
            return new UpdateOrderStatusUseCase(storagePort);
        }
        @Bean
        RemoveItemFromOrderUseCase removeItemFromOrderUseCase(OrderStoragePort storagePort){
            return new RemoveItemFromOrderUseCase(storagePort);
        }
        @Bean
        RemoveAllItemsFromOrderUsecase removeAllItemsFromOrderUsecase(OrderStoragePort storagePort){
            return new RemoveAllItemsFromOrderUsecase(storagePort);
        }
    }
