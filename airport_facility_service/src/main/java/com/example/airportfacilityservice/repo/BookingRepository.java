package com.example.airportfacilityservice.repo;

import com.example.airportfacilityservice.domain.BookingEntity;
import com.example.airportfacilityservice.domain.FacilityType;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for generic bookings.
 */
public interface BookingRepository extends JpaRepository<BookingEntity, UUID> {

    List<BookingEntity> findByPassengerIdOrderByCreatedAtDesc(UUID passengerId);

    List<BookingEntity> findByPassengerIdAndBookingTypeOrderByCreatedAtDesc(UUID passengerId, FacilityType bookingType);
}
