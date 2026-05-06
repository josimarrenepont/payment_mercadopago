package com.projeto.mercadopago.payment.entrypoint.controller;

import com.projeto.mercadopago.payment.core.usecase.CreatePaymentUseCase;
import com.projeto.mercadopago.payment.core.usecase.FindPaymentUseCase;
import com.projeto.mercadopago.payment.core.usecase.model.CreatePaymentCommand;
import com.projeto.mercadopago.payment.core.usecase.model.PaymentResponse;
import com.projeto.mercadopago.payment.entrypoint.dto.PaymentRequestDTO;
import com.projeto.mercadopago.payment.entrypoint.dto.PaymentResponseDTO;
import com.projeto.mercadopago.payment.entrypoint.mapper.PaymentMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private final CreatePaymentUseCase createPaymentUseCase;
    private final FindPaymentUseCase findPaymentUseCase;

    public PaymentController(CreatePaymentUseCase createPaymentUseCase,
                             FindPaymentUseCase findPaymentUseCase) {
        this.createPaymentUseCase = createPaymentUseCase;
        this.findPaymentUseCase = findPaymentUseCase;
    }

    @PostMapping("/checkout")
    public ResponseEntity<PaymentResponseDTO> createCheckout(@RequestBody PaymentRequestDTO request) {

        CreatePaymentCommand command = PaymentMapper.toCommand(request);

        PaymentResponse response = createPaymentUseCase.execute(command);

        PaymentResponseDTO responseDTO = PaymentMapper.toDTO(response);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentResponseDTO> getPayment(@PathVariable Long paymentId) {
        PaymentResponse response = findPaymentUseCase.execute(paymentId);
        PaymentResponseDTO responseDTO = PaymentMapper.toDTO(response);
        return ResponseEntity.ok(responseDTO);
    }
}
