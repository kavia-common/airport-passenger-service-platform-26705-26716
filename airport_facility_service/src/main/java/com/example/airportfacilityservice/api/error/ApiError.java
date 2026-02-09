package com.example.airportfacilityservice.api.error;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.List;

/**
 * Standard API error response body.
 */
@Schema(name = "ApiError", description = "Standard error response")
public record ApiError(
        @Schema(description = "Server timestamp", example = "2026-02-09T10:15:30Z") Instant timestamp,
        @Schema(description = "HTTP status code", example = "400") int status,
        @Schema(description = "HTTP reason phrase", example = "Bad Request") String error,
        @Schema(description = "Human-friendly message", example = "Validation failed") String message,
        @Schema(description = "Request path", example = "/api/facilities") String path,
        @Schema(description = "Field-level validation violations") List<FieldViolation> violations) {

    /**
     * Field-level validation error.
     */
    public record FieldViolation(
            @Schema(description = "Field name", example = "name") String field,
            @Schema(description = "Violation message", example = "must not be blank") String message) {}
}
