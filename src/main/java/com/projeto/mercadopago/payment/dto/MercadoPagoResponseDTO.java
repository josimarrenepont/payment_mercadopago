package com.projeto.mercadopago.payment.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MercadoPagoResponseDTO {

    private Long id;

    @JsonProperty("date_created")
    private String dateCreated;

    private String status;

    @JsonProperty("transaction_amount")
    private BigDecimal transactionAmount;
}
