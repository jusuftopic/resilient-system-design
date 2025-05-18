package org.example.workflowprocessor.service;

import org.example.dto.PaymentCreatedEvent;
import org.example.workflowprocessor.persistance.IncidentStore;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class RetryService
{

    private final IncidentStore incidentStore;


    public void retry(final PaymentCreatedEvent event)
    {
        try
        {
            if (event.getOriginal().isUnRetryable())
            {
                throw new RuntimeException(
                    "Simulated persistent failure for event " + event.getOriginal().getEventId());
            }
            else
            {
                log.info("Successful mock retry for event {}", event.getOriginal().getEventId());
            }
        }
        catch (Exception e)
        {
            incidentStore.put(event.getOriginal().getEventId(), e.getMessage());
        }
    }
}
