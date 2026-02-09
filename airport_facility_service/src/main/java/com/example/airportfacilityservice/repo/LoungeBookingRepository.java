package com.example.airportfacilityservice.repo;

import com.example.airportfacilityservice.domain.LoungeBookingEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for lounge booking details.
 */
public interface LoungeBookingRepository extends JpaRepository<LoungeBookingEntity, UUID> {}
