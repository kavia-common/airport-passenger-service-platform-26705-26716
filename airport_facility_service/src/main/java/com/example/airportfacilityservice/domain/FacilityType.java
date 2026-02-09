package com.example.airportfacilityservice.domain;

/**
 * Facility types supported by the platform.
 *
 * <p>Backed by PostgreSQL enum {@code facility_type} created in Flyway V1.
 */
public enum FacilityType {
    PARKING,
    LOUNGE,
    HOTEL,
    RESTAURANT,
    SERVICE
}
