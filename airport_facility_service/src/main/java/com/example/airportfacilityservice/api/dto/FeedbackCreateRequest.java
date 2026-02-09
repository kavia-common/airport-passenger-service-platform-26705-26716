package com.example.airportfacilityservice.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.UUID;

@Schema(name = "FeedbackCreateRequest", description = "Create feedback")
public record FeedbackCreateRequest(
        @Schema(description = "Passenger ID") UUID passengerId,
        @Schema(description = "Facility ID") UUID facilityId,
        @Min(1) @Max(5) @Schema(description = "Rating 1-5") Integer rating,
        @Size(max = 200) @Schema(description = "Title") String title,
        @NotBlank @Schema(description = "Message") String message) {}
