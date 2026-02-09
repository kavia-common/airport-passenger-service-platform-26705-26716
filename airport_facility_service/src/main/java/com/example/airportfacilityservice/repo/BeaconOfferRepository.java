package com.example.airportfacilityservice.repo;

import com.example.airportfacilityservice.domain.BeaconOfferEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for beacon offers.
 */
public interface BeaconOfferRepository extends JpaRepository<BeaconOfferEntity, UUID> {

    List<BeaconOfferEntity> findByBeaconIdAndActive(String beaconId, boolean active);
}
