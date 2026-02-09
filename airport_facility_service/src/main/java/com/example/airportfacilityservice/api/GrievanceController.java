package com.example.airportfacilityservice.api;

import com.example.airportfacilityservice.api.dto.GrievanceCreateRequest;
import com.example.airportfacilityservice.api.error.NotFoundException;
import com.example.airportfacilityservice.domain.GrievanceEntity;
import com.example.airportfacilityservice.repo.GrievanceRepository;
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
@RequestMapping("/api/grievances")
@Tag(name = "Grievances", description = "Grievance/ticket endpoints")
public class GrievanceController {

    private final GrievanceRepository grievanceRepository;

    public GrievanceController(GrievanceRepository grievanceRepository) {
        this.grievanceRepository = grievanceRepository;
    }

    @GetMapping
    @Operation(summary = "List grievances", description = "Optionally filter by passengerId.")
    public List<GrievanceEntity> list(@RequestParam(required = false) UUID passengerId) {
        if (passengerId != null) {
            return grievanceRepository.findByPassengerIdOrderByCreatedAtDesc(passengerId);
        }
        return grievanceRepository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get grievance by id")
    public GrievanceEntity get(@PathVariable UUID id) {
        return grievanceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Grievance not found: " + id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create grievance")
    public GrievanceEntity create(@Valid @RequestBody GrievanceCreateRequest request) {
        GrievanceEntity entity = new GrievanceEntity();
        entity.setId(UUID.randomUUID());
        entity.setPassengerId(request.passengerId());
        entity.setBookingId(request.bookingId());
        entity.setCategory(request.category());
        entity.setSubject(request.subject());
        entity.setDescription(request.description());
        entity.setPriority(request.priority() != null ? request.priority() : "NORMAL");
        entity.setStatus("OPEN");
        entity.setCreatedAt(Instant.now());
        entity.setUpdatedAt(Instant.now());
        return grievanceRepository.save(entity);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete grievance")
    public void delete(@PathVariable UUID id) {
        if (!grievanceRepository.existsById(id)) {
            throw new NotFoundException("Grievance not found: " + id);
        }
        grievanceRepository.deleteById(id);
    }
}
