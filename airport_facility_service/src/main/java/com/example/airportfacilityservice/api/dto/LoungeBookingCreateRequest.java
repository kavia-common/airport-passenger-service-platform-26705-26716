package com.example.airportfacilityservice.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

@Schema(name = "LoungeBookingCreateRequest", description = "Create lounge booking details (for an existing booking id)")
public record LoungeBookingCreateRequest(
        @NotNull @Schema(description = "Booking ID") UUID bookingId,
        @Min(1) @Schema(description = "Number of lounge passes", example = "1") int loungePassCount) {}
