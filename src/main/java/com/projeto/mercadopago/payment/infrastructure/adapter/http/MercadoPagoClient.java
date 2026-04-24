package com.projeto.mercadopago.payment.infrastructure.adapter.http;

import com.projeto.mercadopago.common.exception.IntegrationException;
import com.projeto.mercadopago.payment.core.port.MercadoPagoGateway;
import io.github.resilience4j.retry.Retry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
@Profile({"dev", "test", "prod"})
public class MercadoPagoClient implements MercadoPagoGateway {

    private final RestTemplate restTemplate;
    private final Retry mercadoPagoRetry;

    @Value("${mercadopago.access.token}")
    private String accessToken;

    public MercadoPagoClient(RestTemplate restTemplate, Retry mercadoPagoRetry) {
        this.restTemplate = restTemplate;
        this.mercadoPagoRetry = mercadoPagoRetry;
    }

    @Override
    public String createCheckoutPreference(BigDecimal amount, String description) {
        try {
            return Retry.decorateSupplier(mercadoPagoRetry, () -> {

                HttpHeaders headers = new HttpHeaders();
                headers.setBearerAuth(accessToken);
                headers.setContentType(MediaType.APPLICATION_JSON);

                Map<String, Object> payload = createPayload(amount, description);

                HttpEntity<Map<String, Object>> entity = new HttpEntity<>(payload, headers);

                ResponseEntity<Map> response = restTemplate.postForEntity(
                        "https://api.mercadopago.com/checkout/preferences",
                        entity,
                        Map.class
                );

                Map body = response.getBody();
                if (body == null || body.get("init_point") == null) {
                    throw new IntegrationException("Failed to generate the payment link on Mercado Pago");
                }

                return body.get("init_point").toString();

            }).get();

        } catch (Throwable throwable) {
            throw new IntegrationException(
                    "Erro após 3 tentativas de conexão com Mercado Pago: " + throwable.getMessage());
        }
    }


    private Map<String, Object> createPayload(BigDecimal amount, String description) {
        Map<String, Object> item = new HashMap<>();
        item.put("title", description);
        item.put("quantity", 1);
        item.put("unit_price", amount);

        Map<String, Object> payload = new HashMap<>();
        payload.put("items", List.of(item));
        return payload;
    }
}
