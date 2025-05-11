package org.example.dto;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FailureEvent
{

    private String uuid;
    private EventPayload original;
    private String reason;
    private Instant timestamp;
}
