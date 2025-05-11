// Note: API DTOs and services are backend duplicates for the sake of simplicity.
// For production code, consider using generators like OpenAPI Generator or Swagger Codegen.
export interface IncidentDTO {
  event: string;
  failReason: string;
}
