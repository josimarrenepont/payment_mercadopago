package com.projeto.mercadopago.payment.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class PaymentStatusDTO {

    private Long externalId;
    private String status;
    private BigDecimal transactionAmount;
    private String currency;
    private String dateCreated;
}
