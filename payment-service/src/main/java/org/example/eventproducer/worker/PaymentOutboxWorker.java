package org.example.eventproducer.worker;

import java.time.LocalDateTime;
import java.util.List;

import org.example.CommonConstants;
import org.example.eventproducer.entity.PaymentOutbox;
import org.example.eventproducer.entity.enums.PaymentOutboxStatus;
import org.example.eventproducer.repository.PaymentOutboxRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * This class is responsible for processing the payment outbox.
 * It sends the pending payment events to the Kafka topic and updates their status.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentOutboxWorker
{
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final PaymentOutboxRepository paymentOutboxRepository;

    /**
     * This method is scheduled to run every 5 seconds to process the payment outbox.
     * It sends the pending payment events to the Kafka topic and updates their status.
     */
    @Scheduled(fixedDelay = 5000)
    public void processPaymentOutbox()
    {
        final List<PaymentOutbox> events = paymentOutboxRepository.findAllByStatus(PaymentOutboxStatus.PENDING);
        log.info("Payment outbox processing started. Found {} events.", events.size());

        for (PaymentOutbox event : events)
        {
            try
            {
                kafkaTemplate.send(CommonConstants.KAFKA_PAYMENT_CREATED_EVENT_TOPIC, event.getPayload());
                event.setStatus(PaymentOutboxStatus.SENT);
                event.setProcessedAt(LocalDateTime.now());
                log.info("Payment outbox event sent successfully: {}", event.getEventId());
            }
            catch (Exception e)
            {
                log.error("Failed to send payment outbox event: {}", event.getEventId(), e);
                event.setStatus(PaymentOutboxStatus.FAILED);
            }
            finally
            {
                paymentOutboxRepository.save(event);
            }
        }
    }
}
