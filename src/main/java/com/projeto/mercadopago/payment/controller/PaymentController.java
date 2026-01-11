package com.projeto.mercadopago.payment.controller;

import com.projeto.mercadopago.payment.domain.Payment;
import com.projeto.mercadopago.payment.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/checkout")
    public ResponseEntity<Map<String, String>> createCheckout() {

        String initPoint = paymentService.createCheckoutPreference();

        return ResponseEntity.ok(
                Map.of("init_point", initPoint)
        );
    }
}
