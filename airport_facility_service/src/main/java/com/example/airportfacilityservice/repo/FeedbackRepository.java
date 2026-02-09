package com.example.airportfacilityservice.repo;

import com.example.airportfacilityservice.domain.FeedbackEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for feedback.
 */
public interface FeedbackRepository extends JpaRepository<FeedbackEntity, UUID> {

    List<FeedbackEntity> findByPassengerIdOrderByCreatedAtDesc(UUID passengerId);

    List<FeedbackEntity> findByFacilityIdOrderByCreatedAtDesc(UUID facilityId);
}
