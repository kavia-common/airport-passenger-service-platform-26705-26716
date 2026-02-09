package com.example.airportfacilityservice.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;

@Schema(name = "HotelBookingCreateRequest", description = "Create hotel booking details (for an existing booking id)")
public record HotelBookingCreateRequest(
        @NotNull @Schema(description = "Booking ID") UUID bookingId,
        @Min(1) @Schema(description = "Guests count", example = "1") int guestsCount,
        @Size(max = 50) @Schema(description = "Room type", example = "DELUXE") String roomType) {}
