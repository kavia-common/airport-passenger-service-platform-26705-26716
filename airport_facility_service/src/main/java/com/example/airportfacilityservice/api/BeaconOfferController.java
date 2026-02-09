package com.example.airportfacilityservice.api;

import com.example.airportfacilityservice.api.dto.BeaconOfferCreateRequest;
import com.example.airportfacilityservice.api.error.NotFoundException;
import com.example.airportfacilityservice.domain.BeaconOfferEntity;
import com.example.airportfacilityservice.repo.BeaconOfferRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/beacon-offers")
@Tag(name = "Beacon Offers", description = "Beacon-based offers/promotions")
public class BeaconOfferController {

    private final BeaconOfferRepository beaconOfferRepository;

    public BeaconOfferController(BeaconOfferRepository beaconOfferRepository) {
        this.beaconOfferRepository = beaconOfferRepository;
    }

    @GetMapping
    @Operation(summary = "List beacon offers", description = "Filter by beaconId and optionally onlyActive.")
    public List<BeaconOfferEntity> list(
            @RequestParam(required = false) String beaconId,
            @RequestParam(required = false, defaultValue = "true") boolean onlyActive) {

        if (beaconId != null) {
            return beaconOfferRepository.findByBeaconIdAndActive(beaconId, onlyActive);
        }
        return beaconOfferRepository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get beacon offer by id")
    public BeaconOfferEntity get(@PathVariable UUID id) {
        return beaconOfferRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Beacon offer not found: " + id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create beacon offer")
    public BeaconOfferEntity create(@Valid @RequestBody BeaconOfferCreateRequest request) {
        BeaconOfferEntity entity = new BeaconOfferEntity();
        entity.setId(UUID.randomUUID());
        entity.setBeaconId(request.beaconId());
        entity.setTitle(request.title());
        entity.setMessage(request.message());
        entity.setActive(request.active() == null || request.active());
        entity.setMetadata(request.metadata());
        entity.setCreatedAt(Instant.now());
        entity.setUpdatedAt(Instant.now());
        return beaconOfferRepository.save(entity);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete beacon offer")
    public void delete(@PathVariable UUID id) {
        if (!beaconOfferRepository.existsById(id)) {
            throw new NotFoundException("Beacon offer not found: " + id);
        }
        beaconOfferRepository.deleteById(id);
    }
}
