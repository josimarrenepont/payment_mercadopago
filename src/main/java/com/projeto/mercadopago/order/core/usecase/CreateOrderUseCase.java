    package com.projeto.mercadopago.order.core.usecase;

    import com.projeto.mercadopago.order.core.domain.Order;
    import com.projeto.mercadopago.order.core.domain.OrderItem;
    import com.projeto.mercadopago.order.core.domain.exception.InvalidOrderOperationException;
    import com.projeto.mercadopago.order.core.port.CouponStoragePort;
    import com.projeto.mercadopago.order.core.port.OrderStoragePort;
    import com.projeto.mercadopago.order.core.usecase.model.CreateOrderCommand;
    import com.projeto.mercadopago.order.core.usecase.model.OrderResponse;

    public class CreateOrderUseCase {
        private final OrderStoragePort storagePort;
        private final CouponStoragePort couponStoragePort;

        public CreateOrderUseCase(OrderStoragePort storagePort, CouponStoragePort couponStoragePort) {
            this.storagePort = storagePort;
            this.couponStoragePort = couponStoragePort;
        }

        public OrderResponse execute(CreateOrderCommand command) {

            if (command.description() == null || command.description().isBlank()) {
                throw new InvalidOrderOperationException("Order description is required");
            }

            if (command.items() == null || command.items().isEmpty()) {
                throw new InvalidOrderOperationException("Cannot create an order without items");
            }

            Order order = new Order(command.description());

            command.items().forEach(itemDto ->
                    order.addItem(new OrderItem(null, itemDto.productId(), itemDto.price(), itemDto.quantity()))
            );

            if (command.couponCode() != null && !command.couponCode().isBlank()) {
                couponStoragePort.findValidCoupon(command.couponCode())
                        .ifPresentOrElse(
                                order::applyDiscount,
                                () -> { throw new InvalidOrderOperationException("Coupon invalid or expired: " + command.couponCode()); }
                        );
            }

            Order savedOrder = storagePort.save(order);

            return OrderResponse.fromDomain(savedOrder);
        }
    }