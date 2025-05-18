package org.example.eventconsumer.processor;

import org.example.CommonConstants;
import org.example.dto.PaymentCreatedEvent;
import org.example.eventconsumer.service.InvoiceService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Processor class for handling payment created events.
 * This class listens to Kafka messages and processes them accordingly.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentCreatedEventProcessor
{

    private final InvoiceService invoiceService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Processes the payment created event.
     * @param payload the payment created event payload
     */
    @KafkaListener(topics = CommonConstants.KAFKA_PAYMENT_EVENTS_TOPIC, groupId = "invoice-consumer-group")
    public void process(String payload)
    {
        try
        {
            PaymentCreatedEvent event = objectMapper.readValue(payload, PaymentCreatedEvent.class);
            log.info("Received event for processing invoice: {}", event.getInvoiceId());
            invoiceService.updateInvoice(event);        }
        catch (Exception e)
        {
            log.error("Failed to process payment created event: {}", payload, e);
        }
    }
}
