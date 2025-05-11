package org.example.workflowprocessor.persistance;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

/**
 * Mock storage for escalation incidents.
 */
@Component
public class IncidentStore
{

    Map<String, String> incidents = new HashMap<>();

    public void put(String key, String value)
    {
        incidents.put(key, value);
    }

    public Map<String, String> getAll()
    {
        return incidents;
    }
}
