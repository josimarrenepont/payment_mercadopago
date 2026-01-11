package com.projeto.mercadopago.payment.gateway;

import com.projeto.mercadopago.payment.exception.IntegrationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Profile("prod")
public class MercadoPagoClient implements MercadoPagoGateway {

    private static final String PREFERENCES_URL =
            "https://api.mercadopago.com/checkout/preferences";

    private final RestTemplate restTemplate;

    @Value("${mercadopago.access.token}")
    private String accessToken;

    public MercadoPagoClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public String createCheckoutPreference() {

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> item = new HashMap<>();
        item.put("title", "Pagamento Teste");
        item.put("quantity", 1);
        item.put("unit_price", 100);

        Map<String, Object> payload = new HashMap<>();
        payload.put("items", List.of(item));

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(payload, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(PREFERENCES_URL, entity, Map.class);

        Map body = response.getBody();

        if (body == null || !body.containsKey("init_point")) {
            throw new IntegrationException("Failed to create checkout preference");
        }

        return body.get("init_point").toString();
    }
}
