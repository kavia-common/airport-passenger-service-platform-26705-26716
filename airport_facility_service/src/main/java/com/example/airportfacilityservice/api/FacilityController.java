package com.example.airportfacilityservice.api;

import com.example.airportfacilityservice.api.dto.FacilityCreateRequest;
import com.example.airportfacilityservice.api.dto.FacilityUpdateRequest;
import com.example.airportfacilityservice.api.error.ConflictException;
import com.example.airportfacilityservice.api.error.NotFoundException;
import com.example.airportfacilityservice.domain.FacilityEntity;
import com.example.airportfacilityservice.domain.FacilityType;
import com.example.airportfacilityservice.repo.FacilityRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/facilities")
@Tag(name = "Facilities", description = "Facilities catalog endpoints")
public class FacilityController {

    private final FacilityRepository facilityRepository;

    public FacilityController(FacilityRepository facilityRepository) {
        this.facilityRepository = facilityRepository;
    }

    @GetMapping
    @Operation(summary = "List facilities", description = "Optionally filter by facilityType and active flag.")
    public List<FacilityEntity> list(
            @RequestParam(required = false) FacilityType facilityType,
            @RequestParam(required = false) Boolean active) {

        if (facilityType != null && active != null) {
            return facilityRepository.findByFacilityTypeAndActive(facilityType, active);
        }
        return facilityRepository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get facility by id")
    public FacilityEntity get(@PathVariable UUID id) {
        return facilityRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Facility not found: " + id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create facility")
    public FacilityEntity create(@Valid @RequestBody FacilityCreateRequest request) {
        facilityRepository.findByFacilityTypeAndCode(request.facilityType(), request.code())
                .ifPresent(existing -> {
                    throw new ConflictException("Facility already exists for type+code: " + request.facilityType() + "/" + request.code());
                });

        FacilityEntity entity = new FacilityEntity();
        entity.setId(UUID.randomUUID());
        entity.setFacilityType(request.facilityType());
        entity.setCode(request.code());
        entity.setName(request.name());
        entity.setDescription(request.description());
        entity.setLocationText(request.locationText());
        entity.setActive(request.active() == null || request.active());
        entity.setPriceBase(request.priceBase());
        entity.setCurrencyCode(request.currencyCode() != null ? request.currencyCode() : "INR");
        entity.setMetadata(request.metadata());
        entity.setCreatedAt(Instant.now());
        entity.setUpdatedAt(Instant.now());

        try {
            return facilityRepository.save(entity);
        } catch (DataIntegrityViolationException ex) {
            throw new ConflictException("Facility violates uniqueness constraints");
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update facility")
    public FacilityEntity update(@PathVariable UUID id, @Valid @RequestBody FacilityUpdateRequest request) {
        FacilityEntity entity = facilityRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Facility not found: " + id));

        if (request.name() != null) {
            entity.setName(request.name());
        }
        if (request.description() != null) {
            entity.setDescription(request.description());
        }
        if (request.locationText() != null) {
            entity.setLocationText(request.locationText());
        }
        if (request.active() != null) {
            entity.setActive(request.active());
        }
        if (request.priceBase() != null) {
            entity.setPriceBase(request.priceBase());
        }
        if (request.currencyCode() != null) {
            entity.setCurrencyCode(request.currencyCode());
        }
        if (request.metadata() != null) {
            entity.setMetadata(request.metadata());
        }
        entity.setUpdatedAt(Instant.now());

        return facilityRepository.save(entity);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete facility")
    public void delete(@PathVariable UUID id) {
        if (!facilityRepository.existsById(id)) {
            throw new NotFoundException("Facility not found: " + id);
        }
        facilityRepository.deleteById(id);
    }
}
