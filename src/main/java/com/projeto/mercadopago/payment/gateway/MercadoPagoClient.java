package com.projeto.mercadopago.payment.gateway;

import com.projeto.mercadopago.payment.domain.Payment;
import com.projeto.mercadopago.payment.dto.MercadoPagoResponseDTO;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.boot.beanvalidation.IntegrationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;


@Component
@AllArgsConstructor
public class MercadoPagoClient implements MercadoPagoGateway{

    private final RestTemplate restTemplate;

    @Value("${mercadopago.access.token}")
    private String token;

    private static final String API_URL = "http://api.mercadopago.com/v1/payments";

    @Override
    public Payment findById(String id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        try {
            MercadoPagoResponseDTO response =
                    restTemplate.exchange(
                            "http://api.mercadopago.com/v1/payments/" + id,
                            HttpMethod.GET,
                            entity,
                            MercadoPagoResponseDTO.class
                    ).getBody();

            return new Payment(
                    Objects.requireNonNull(response).getId(),
                    response.getDateCreated(),
                    response.getStatus(),
                    response.getTransactionAmount()
            );
        } catch (Exception e){
            throw new IntegrationException("Error checking payment");
        }
    }
}
