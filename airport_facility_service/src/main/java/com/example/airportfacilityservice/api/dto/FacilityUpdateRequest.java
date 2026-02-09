package com.example.airportfacilityservice.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

@Schema(name = "FacilityUpdateRequest", description = "Update a facility catalog entry")
public record FacilityUpdateRequest(
        @Size(max = 200) @Schema(description = "Display name") String name,
        @Schema(description = "Description") String description,
        @Size(max = 255) @Schema(description = "Location description") String locationText,
        @Schema(description = "Active flag") Boolean active,
        @Schema(description = "Base price") BigDecimal priceBase,
        @Size(max = 3) @Schema(description = "Currency code") String currencyCode,
        @Schema(description = "JSON metadata as string") String metadata) {}
