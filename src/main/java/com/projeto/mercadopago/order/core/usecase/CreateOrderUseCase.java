package com.projeto.mercadopago.order.core.usecase;

import com.projeto.mercadopago.order.core.domain.exception.InvalidOrderOperationException;
import com.projeto.mercadopago.order.core.port.CouponStoragePort;
import com.projeto.mercadopago.order.core.port.OrderStoragePort;
import com.projeto.mercadopago.order.core.domain.Order;

public class CreateOrderUseCase {
    private final OrderStoragePort storagePort;
    private final CouponStoragePort couponStoragePort;

    public CreateOrderUseCase(OrderStoragePort storagePort, CouponStoragePort couponStoragePort) {
        this.storagePort = storagePort;
        this.couponStoragePort = couponStoragePort;
    }

    public Order execute(Order order, String couponCode){
        if(order.getItems().isEmpty()){
            throw new InvalidOrderOperationException("Cannot create an order without items");
        }
        if(couponCode != null && !couponCode.isBlank()){
            couponStoragePort.findValidCoupon(couponCode)
                    .ifPresentOrElse(
                            order::applyDiscount,
                            () -> {
                                throw new InvalidOrderOperationException("Coupon invalid or expired");
                            }
                    );
        }
        return storagePort.save(order);
    }
}
