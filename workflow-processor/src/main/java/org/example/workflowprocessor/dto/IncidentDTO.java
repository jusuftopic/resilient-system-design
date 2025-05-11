package org.example.workflowprocessor.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class IncidentDTO
{
    private String event;
    private String failReason;
}
