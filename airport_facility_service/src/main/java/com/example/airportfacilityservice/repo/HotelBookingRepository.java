package com.example.airportfacilityservice.repo;

import com.example.airportfacilityservice.domain.HotelBookingEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for hotel booking details.
 */
public interface HotelBookingRepository extends JpaRepository<HotelBookingEntity, UUID> {}
