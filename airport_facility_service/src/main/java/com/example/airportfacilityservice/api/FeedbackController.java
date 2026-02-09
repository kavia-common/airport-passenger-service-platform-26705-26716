package com.example.airportfacilityservice.api;

import com.example.airportfacilityservice.api.dto.FeedbackCreateRequest;
import com.example.airportfacilityservice.api.error.NotFoundException;
import com.example.airportfacilityservice.domain.FeedbackEntity;
import com.example.airportfacilityservice.repo.FeedbackRepository;
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
@RequestMapping("/api/feedback")
@Tag(name = "Feedback", description = "Feedback endpoints")
public class FeedbackController {

    private final FeedbackRepository feedbackRepository;

    public FeedbackController(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    @GetMapping
    @Operation(summary = "List feedback", description = "Filter by passengerId or facilityId.")
    public List<FeedbackEntity> list(
            @RequestParam(required = false) UUID passengerId,
            @RequestParam(required = false) UUID facilityId) {

        if (passengerId != null) {
            return feedbackRepository.findByPassengerIdOrderByCreatedAtDesc(passengerId);
        }
        if (facilityId != null) {
            return feedbackRepository.findByFacilityIdOrderByCreatedAtDesc(facilityId);
        }
        return feedbackRepository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get feedback by id")
    public FeedbackEntity get(@PathVariable UUID id) {
        return feedbackRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Feedback not found: " + id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create feedback")
    public FeedbackEntity create(@Valid @RequestBody FeedbackCreateRequest request) {
        FeedbackEntity entity = new FeedbackEntity();
        entity.setId(UUID.randomUUID());
        entity.setPassengerId(request.passengerId());
        entity.setFacilityId(request.facilityId());
        entity.setRating(request.rating());
        entity.setTitle(request.title());
        entity.setMessage(request.message());
        entity.setStatus("OPEN");
        entity.setCreatedAt(Instant.now());
        entity.setUpdatedAt(Instant.now());
        return feedbackRepository.save(entity);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete feedback")
    public void delete(@PathVariable UUID id) {
        if (!feedbackRepository.existsById(id)) {
            throw new NotFoundException("Feedback not found: " + id);
        }
        feedbackRepository.deleteById(id);
    }
}
