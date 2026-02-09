package com.example.airportfacilityservice.repo;

import com.example.airportfacilityservice.domain.FacilityEntity;
import com.example.airportfacilityservice.domain.FacilityType;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for facilities catalog.
 */
public interface FacilityRepository extends JpaRepository<FacilityEntity, UUID> {

    Optional<FacilityEntity> findByFacilityTypeAndCode(FacilityType facilityType, String code);

    List<FacilityEntity> findByFacilityTypeAndActive(FacilityType facilityType, boolean active);
}
