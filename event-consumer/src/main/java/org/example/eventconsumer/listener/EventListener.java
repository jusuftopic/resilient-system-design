package org.example.eventconsumer.listener;

import org.example.CommonConstants;
import org.example.dto.EventPayload;
import org.example.eventconsumer.service.EventProcessor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class EventListener
{

    private final EventProcessor eventProcessor;

    @KafkaListener(topics = CommonConstants.KAFKA_BUSINESS_TOPIC, groupId = "event-consumer-group")
    public void processEvent(EventPayload payload)
    {
        log.info("Received event for processing: {}", payload.getEventId());
        eventProcessor.process(payload);
    }
}
