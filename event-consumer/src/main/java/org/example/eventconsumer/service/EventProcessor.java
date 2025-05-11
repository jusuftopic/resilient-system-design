package org.example.eventconsumer.service;

import org.example.dto.EventPayload;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventProcessor
{

    private final FailurePublisher failurePublisher;


    public void process(EventPayload payload)
    {

        // Simulate processing logic
        try {
            if ("fail".equalsIgnoreCase(payload.getType()))
            {
                throw new RuntimeException("Simulated failure");
            }
            else
            {
                log.info("Successful processing of event: {}", payload.getEventId());
            }
        }
        catch (Exception e)
        {
            log.error("Fail to process event: {}. Delegation to workflow", payload.getEventId());
            failurePublisher.publish(payload, e.getMessage());
        }
    }
}
