package com.example.airportfacilityservice.repo;

import com.example.airportfacilityservice.domain.ParkingBookingEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for parking booking details.
 */
public interface ParkingBookingRepository extends JpaRepository<ParkingBookingEntity, UUID> {}
