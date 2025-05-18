package org.example.eventproducer.controller;

import java.util.Objects;

import org.example.eventproducer.dto.PaymentRequestDTO;
import org.example.eventproducer.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

/**
 * Controller for handling payment requests.
 */
@RestController
@RequiredArgsConstructor
public class PaymentController
{
    private final PaymentService paymentService;

    /**
     * Processes a payment request.
     * @param payment the payment request
     * @return a response entity indicating the status of the request
     */
    @PostMapping(path = "/api/v1/payment/process", consumes = "application/json")
    public ResponseEntity<Void> processPayment(@RequestBody PaymentRequestDTO payment)
    {
        Objects.requireNonNull(payment, "Request cannot be null");
        paymentService.process(payment);
        return ResponseEntity.accepted().build();
    }
}
