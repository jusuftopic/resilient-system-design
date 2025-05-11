package org.example.workflowprocessor.service;

import java.util.List;

import org.example.workflowprocessor.dto.IncidentDTO;
import org.example.workflowprocessor.persistance.IncidentStore;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IncidentService
{

    private final IncidentStore incidentStore;

    public List<IncidentDTO> getIncidents()
    {
        return incidentStore.getAll().entrySet().stream()
            .map(incident -> IncidentDTO.builder()
                .event(incident.getKey())
                .failReason(incident.getValue())
                .build())
            .toList();
    }
}
