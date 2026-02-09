package com.example.airportfacilityservice.api.dto;

import com.example.airportfacilityservice.domain.FacilityType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

@Schema(name = "FacilityCreateRequest", description = "Create a facility catalog entry")
public record FacilityCreateRequest(
        @NotNull @Schema(description = "Facility type", example = "PARKING") FacilityType facilityType,
        @NotBlank @Size(max = 60) @Schema(description = "Unique code within type", example = "PARK_STD") String code,
        @NotBlank @Size(max = 200) @Schema(description = "Display name", example = "Standard Parking") String name,
        @Schema(description = "Description") String description,
        @Size(max = 255) @Schema(description = "Location description", example = "T1 - Parking Zone A") String locationText,
        @Schema(description = "Active flag", example = "true") Boolean active,
        @Schema(description = "Base price", example = "100.00") BigDecimal priceBase,
        @Size(max = 3) @Schema(description = "Currency code", example = "INR") String currencyCode,
        @Schema(description = "JSON metadata as string") String metadata) {}
