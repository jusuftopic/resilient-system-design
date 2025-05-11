import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { IncidentDTO } from '../models/incident.model';

// Note: API DTOs and services are backend duplicates for the sake of simplicity.
// For production code, consider using generators like OpenAPI Generator or Swagger Codegen.
@Injectable({
  providedIn: 'root'
})
export class IncidentService {

  constructor(private httpClient: HttpClient) { }

  public getIncidents(): Observable<IncidentDTO[]> {
    return this.httpClient.get<IncidentDTO[]>('http://localhost:8082/api/incidents');
  }
}
