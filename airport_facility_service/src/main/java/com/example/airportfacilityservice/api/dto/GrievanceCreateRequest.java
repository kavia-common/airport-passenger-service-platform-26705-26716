package com.example.airportfacilityservice.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.UUID;

@Schema(name = "GrievanceCreateRequest", description = "Create a grievance/ticket")
public record GrievanceCreateRequest(
        @Schema(description = "Passenger ID") UUID passengerId,
        @Schema(description = "Booking ID") UUID bookingId,
        @Size(max = 80) @Schema(description = "Category") String category,
        @NotBlank @Size(max = 200) @Schema(description = "Subject") String subject,
        @NotBlank @Schema(description = "Description") String description,
        @Schema(description = "Priority", example = "NORMAL") String priority) {}
