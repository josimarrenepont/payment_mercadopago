package com.projeto.mercadopago.payment.domain;

import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    private Long id;
    private String dateCreated;
    private String status;
    private BigDecimal transactionAmount;
}
