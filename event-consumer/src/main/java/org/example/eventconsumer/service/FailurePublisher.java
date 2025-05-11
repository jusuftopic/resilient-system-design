package org.example.eventconsumer.service;

import java.time.Instant;
import java.util.UUID;

import org.example.CommonConstants;
import org.example.dto.EventPayload;
import org.example.dto.FailureEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FailurePublisher
{
    private final KafkaTemplate<String, FailureEvent> kafkaTemplate;

    public void publishFailure(EventPayload original, String reason)
    {
        final FailureEvent failureEvent = new FailureEvent(
            UUID.randomUUID().toString(),
            original, reason, Instant.now()
        );
        kafkaTemplate.send(CommonConstants.KAFKA_FAILURE_TOPIC, failureEvent);
    }
}
