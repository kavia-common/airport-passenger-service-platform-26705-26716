package com.example.airportfacilityservice.api;

import com.example.airportfacilityservice.api.dto.BookingCreateRequest;
import com.example.airportfacilityservice.api.error.NotFoundException;
import com.example.airportfacilityservice.domain.BookingEntity;
import com.example.airportfacilityservice.domain.FacilityEntity;
import com.example.airportfacilityservice.domain.FacilityType;
import com.example.airportfacilityservice.repo.BookingRepository;
import com.example.airportfacilityservice.repo.FacilityRepository;
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
@RequestMapping("/api/bookings")
@Tag(name = "Bookings", description = "Generic facility bookings")
public class BookingController {

    private final BookingRepository bookingRepository;
    private final FacilityRepository facilityRepository;

    public BookingController(BookingRepository bookingRepository, FacilityRepository facilityRepository) {
        this.bookingRepository = bookingRepository;
        this.facilityRepository = facilityRepository;
    }

    @GetMapping
    @Operation(summary = "List bookings", description = "Filter by passengerId and optional bookingType.")
    public List<BookingEntity> list(
            @RequestParam UUID passengerId,
            @RequestParam(required = false) FacilityType bookingType) {

        if (bookingType != null) {
            return bookingRepository.findByPassengerIdAndBookingTypeOrderByCreatedAtDesc(passengerId, bookingType);
        }
        return bookingRepository.findByPassengerIdOrderByCreatedAtDesc(passengerId);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get booking by id")
    public BookingEntity get(@PathVariable UUID id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Booking not found: " + id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create booking")
    public BookingEntity create(@Valid @RequestBody BookingCreateRequest request) {
        FacilityEntity facility = facilityRepository.findById(request.facilityId())
                .orElseThrow(() -> new NotFoundException("Facility not found: " + request.facilityId()));

        BookingEntity entity = new BookingEntity();
        entity.setId(UUID.randomUUID());
        entity.setPassengerId(request.passengerId());
        entity.setFacilityId(facility.getId());
        entity.setBookingType(request.bookingType());
        entity.setReferenceCode(request.referenceCode());
        entity.setStartAt(request.startAt());
        entity.setEndAt(request.endAt());
        entity.setAmount(request.amount());
        entity.setCurrencyCode(request.currencyCode() != null ? request.currencyCode() : "INR");
        entity.setNotes(request.notes());
        entity.setStatus(request.status() != null ? request.status() : "PENDING");
        entity.setCreatedAt(Instant.now());
        entity.setUpdatedAt(Instant.now());

        // Note: specialized booking details are created via their own endpoints after this.
        return bookingRepository.save(entity);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete booking")
    public void delete(@PathVariable UUID id) {
        if (!bookingRepository.existsById(id)) {
            throw new NotFoundException("Booking not found: " + id);
        }
        bookingRepository.deleteById(id);
    }
}
