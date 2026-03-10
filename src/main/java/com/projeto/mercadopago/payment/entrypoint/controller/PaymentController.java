package com.projeto.mercadopago.payment.entrypoint.controller;

import com.projeto.mercadopago.payment.core.domain.Payment;
import com.projeto.mercadopago.payment.core.usecase.CreateCheckoutUseCase;
import com.projeto.mercadopago.payment.core.usecase.FindPaymentUseCase;
import com.projeto.mercadopago.payment.entrypoint.dto.PaymentRequestDTO;
import com.projeto.mercadopago.payment.entrypoint.dto.PaymentStatusDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private final CreateCheckoutUseCase createCheckoutUseCase;
    private final FindPaymentUseCase findPaymentUseCase;

    public PaymentController(CreateCheckoutUseCase createCheckoutUseCase,
                             FindPaymentUseCase findPaymentUseCase) {
        this.createCheckoutUseCase = createCheckoutUseCase;
        this.findPaymentUseCase = findPaymentUseCase;
    }

    @PostMapping("/checkout")
    @Operation(summary = "Create checkout", description = "Creates a checkout session for a payment.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Checkout created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    public ResponseEntity<PaymentStatusDTO> createCheckout(@RequestBody PaymentRequestDTO request) {
        Payment payment = new Payment(
                request.productId(),
                "PENDING",
                request.amount()
        );

        String initPOint = createCheckoutUseCase.execute(payment);

        PaymentStatusDTO response = new PaymentStatusDTO(
                payment.getId(),
                payment.getStatus(),
                payment.getTransactionAmount(),
                "BR",
                Instant.now()
        );

        return ResponseEntity.ok().body(response);
    }
    @GetMapping("/{id}")
    @Operation(summary = "Search Payment for ID", description = "Returns the payment details based on the provided ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payment successfully found"),
            @ApiResponse(responseCode = "404", description = "Payment not found")
    })
    public ResponseEntity<PaymentStatusDTO> getPaymentStatus(@PathVariable Long id){
        Payment payment = findPaymentUseCase.execute(id);

        PaymentStatusDTO dto = new PaymentStatusDTO(
                payment.getId(),
                payment.getStatus(),
                payment.getTransactionAmount(),
                "BR",
                Instant.now()
        );
        return ResponseEntity.ok(dto);
    }
}
