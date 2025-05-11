package org.example.dto;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventPayload
{
    private String eventId;
    private String type;
    private String payload;
    private Instant timestamp;
    private boolean unRetryable;
}
