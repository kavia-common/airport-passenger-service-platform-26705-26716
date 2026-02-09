package com.example.airportfacilityservice.repo;

import com.example.airportfacilityservice.domain.GrievanceEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for grievances.
 */
public interface GrievanceRepository extends JpaRepository<GrievanceEntity, UUID> {

    List<GrievanceEntity> findByPassengerIdOrderByCreatedAtDesc(UUID passengerId);
}
