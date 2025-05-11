package org.example.workflowprocessor.controller;

import java.util.List;

import org.example.workflowprocessor.dto.IncidentDTO;
import org.example.workflowprocessor.service.IncidentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class IncidentController
{
    private final IncidentService incidentService;

     @GetMapping("/api/incidents")
     public ResponseEntity<List<IncidentDTO>> getIncidents() {
       return ResponseEntity.ok(incidentService.getIncidents());
    }
}
