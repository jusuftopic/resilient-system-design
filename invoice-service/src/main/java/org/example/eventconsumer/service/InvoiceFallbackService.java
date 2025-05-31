package org.example.eventconsumer.service;

import static org.example.CommonConstants.*;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.example.CommonConstants;
import org.example.dto.PaymentCreatedEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Service responsible for handling fallback operations when invoice processing fails.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class InvoiceFallbackService
{

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    /**
     * Sends an invoice update to the appropriate Kafka topic based on the retry count.
     *
     * @param payload the PaymentCreatedEvent containing the payment details
     */
    public void sendInvoiceUpdate(final PaymentCreatedEvent payload)
    {
        if (payload.getRetryCount() >= PaymentCreatedEvent.MAX_RETRIES)
        {
            kafkaTemplate.send(KAFKA_PAYMENT_CREATED_EVENT_DLQ_TOPIC, getPayloadAsString(payload));
        }
        else
        {
            scheduler.schedule(() -> {
                kafkaTemplate.send(KAFKA_PAYMENT_CREATED_EVENT_5S_RETRY_TOPIC, getPayloadAsString(payload));},
            5, java.util.concurrent.TimeUnit.SECONDS);
        }
    }

    private String getPayloadAsString(final PaymentCreatedEvent payload)
    {
        try
        {
            return objectMapper.writeValueAsString(payload);
        }
        catch (JsonProcessingException e)
        {
            throw new IllegalStateException("Failed to serialize payload: " + payload, e);
        }
    }
}
