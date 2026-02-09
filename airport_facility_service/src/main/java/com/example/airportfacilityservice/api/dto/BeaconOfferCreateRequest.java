package com.example.airportfacilityservice.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(name = "BeaconOfferCreateRequest", description = "Create a beacon offer")
public record BeaconOfferCreateRequest(
        @NotBlank @Size(max = 80) @Schema(description = "Beacon identifier", example = "BEACON-T1-001") String beaconId,
        @NotBlank @Size(max = 200) @Schema(description = "Title") String title,
        @NotBlank @Schema(description = "Message") String message,
        @Schema(description = "Active flag") Boolean active,
        @Schema(description = "JSON metadata as string") String metadata) {}
