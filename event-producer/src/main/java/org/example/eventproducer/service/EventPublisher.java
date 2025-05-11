package org.example.eventproducer.service;

import static org.example.CommonConstants.*;

import org.example.dto.EventPayload;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventPublisher
{

    private final KafkaTemplate<String, EventPayload> kafkaTemplate;

    public void publish(final EventPayload payload)
    {
        log.info("Publishing event {} to Kafka topic: {}",
            payload.getEventId(), KAFKA_BUSINESS_TOPIC);
        kafkaTemplate.send(KAFKA_BUSINESS_TOPIC, payload);
    }
}
