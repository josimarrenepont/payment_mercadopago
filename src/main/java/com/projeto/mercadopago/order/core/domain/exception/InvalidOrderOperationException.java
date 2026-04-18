package com.projeto.mercadopago.order.core.domain.exception;

public class InvalidOrderOperationException extends RuntimeException{
    public InvalidOrderOperationException(String message){
        super(message);
    }
}
