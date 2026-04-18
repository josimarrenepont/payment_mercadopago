package com.projeto.mercadopago.order.core.domain.exception;

public class OrderAlreadyPaidException extends InvalidOrderOperationException{

    public OrderAlreadyPaidException(String message){
        super(message);
    }
}

