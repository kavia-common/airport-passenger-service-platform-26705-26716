package com.example.airportfacilityservice.api.dto;

import com.example.airportfacilityservice.domain.FacilityType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Schema(name = "BookingCreateRequest", description = "Create a generic booking")
public record BookingCreateRequest(
        @NotNull @Schema(description = "Passenger ID") UUID passengerId,
        @NotNull @Schema(description = "Facility ID") UUID facilityId,
        @NotNull @Schema(description = "Booking type", example = "PARKING") FacilityType bookingType,
        @NotBlank @Size(max = 40) @Schema(description = "Reference code", example = "BK-12345") String referenceCode,
        @Schema(description = "Start time") Instant startAt,
        @Schema(description = "End time") Instant endAt,
        @Schema(description = "Amount") BigDecimal amount,
        @Size(max = 3) @Schema(description = "Currency code", example = "INR") String currencyCode,
        @Schema(description = "Notes") String notes,
        @Schema(description = "Status", example = "PENDING") String status) {}
