package com.example.airportfacilityservice.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;

@Schema(name = "ParkingBookingCreateRequest", description = "Create parking booking details (for an existing booking id)")
public record ParkingBookingCreateRequest(
        @NotNull @Schema(description = "Booking ID") UUID bookingId,
        @NotBlank @Size(max = 20) @Schema(description = "Vehicle number", example = "KL-07-AB-1234") String vehicleNumber,
        @Size(max = 30) @Schema(description = "Slot code") String slotCode) {}
