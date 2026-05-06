package com.projeto.mercadopago.payment.core.exception;

public class InvalidPaymentOperationException extends RuntimeException{
    public InvalidPaymentOperationException(String message){
        super(message);
    }
}
