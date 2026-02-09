package com.example.airportfacilityservice.api.error;

/**
 * Thrown when a request violates a uniqueness/business constraint.
 */
public class ConflictException extends RuntimeException {

    public ConflictException(String message) {
        super(message);
    }
}
