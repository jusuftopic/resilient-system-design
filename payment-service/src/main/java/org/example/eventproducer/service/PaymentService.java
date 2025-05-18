package org.example.eventproducer.service;

import jakarta.transaction.Transactional;

import org.example.dto.PaymentCreatedEvent;
import org.example.eventproducer.dto.PaymentRequestDTO;
import org.example.eventproducer.entity.Payment;
import org.example.eventproducer.entity.PaymentOutbox;
import org.example.eventproducer.mapper.PaymentMapper;
import org.example.eventproducer.repository.PaymentOutboxRepository;
import org.example.eventproducer.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Service class for processing payments.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService
{

    private final PaymentRepository paymentRepository;
    private final PaymentOutboxRepository paymentOutboxRepository;
    private final PaymentMapper paymentMapper;
    private final ObjectMapper objectMapper;

    /**
     * Processes a payment request using outbox pattern.
     *
     * @param request the payment request
     */
    @Transactional
    public void process(final PaymentRequestDTO request)
    {
        log.info("Processing payment: {}", request);
        final Payment saved = paymentRepository.save(
            paymentMapper.map(request));

        final PaymentOutbox outbox = new PaymentOutbox();
        outbox.setAggregateId(saved.getPaymentReference());
        outbox.setEventType("PAYMENT_CREATED");
        outbox.setPayload(createPaymentPayload(saved));
        paymentOutboxRepository.save(outbox);

    }

    private String createPaymentPayload(Payment payment)
    {
       final PaymentCreatedEvent event = PaymentCreatedEvent.builder()
           .paymentReference(payment.getPaymentReference())
           .amount(payment.getAmount())
           .currency(payment.getCurrency())
           .invoiceId(payment.getInvoiceId())
           .build();

       try
       {
           return objectMapper.writeValueAsString(event);
       }
       catch (final JsonProcessingException e)
       {
           log.error("Error serializing event: {}", e.getMessage());
           throw new RuntimeException("Error serializing event", e);
       }
    }
}
