package org.example.eventproducer.controller;

import java.util.Objects;

import org.example.dto.EventPayload;
import org.example.eventproducer.service.EventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class EventProducer
{
    private final EventPublisher eventPublisher;

    @PostMapping
    public ResponseEntity<Void> publishEvent(@RequestBody EventPayload payload)
    {
        Objects.requireNonNull(payload, "Payload cannot be null");
        eventPublisher.publish(payload);
        return ResponseEntity.accepted().build();
    }
}
