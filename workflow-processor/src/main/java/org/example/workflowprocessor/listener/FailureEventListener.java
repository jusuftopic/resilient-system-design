package org.example.workflowprocessor.listener;

import org.example.CommonConstants;
import org.example.dto.FailureEvent;
import org.example.workflowprocessor.service.RetryService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FailureEventListener
{
    private final RetryService retryService;

    @KafkaListener(topics = CommonConstants.KAFKA_FAILURE_TOPIC, groupId = "workflow-processor-group")
    public void handleFailure(final FailureEvent event)
    {
        retryService.retry(event);
    }

}
