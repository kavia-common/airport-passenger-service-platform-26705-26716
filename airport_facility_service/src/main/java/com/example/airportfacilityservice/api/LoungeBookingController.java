package com.example.airportfacilityservice.api;

import com.example.airportfacilityservice.api.dto.LoungeBookingCreateRequest;
import com.example.airportfacilityservice.api.error.NotFoundException;
import com.example.airportfacilityservice.domain.BookingEntity;
import com.example.airportfacilityservice.domain.LoungeBookingEntity;
import com.example.airportfacilityservice.repo.BookingRepository;
import com.example.airportfacilityservice.repo.LoungeBookingRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.time.Instant;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/lounge-bookings")
@Tag(name = "Lounge Bookings", description = "Lounge-specific booking details")
public class LoungeBookingController {

    private final BookingRepository bookingRepository;
    private final LoungeBookingRepository loungeBookingRepository;

    public LoungeBookingController(BookingRepository bookingRepository, LoungeBookingRepository loungeBookingRepository) {
        this.bookingRepository = bookingRepository;
        this.loungeBookingRepository = loungeBookingRepository;
    }

    @GetMapping("/{bookingId}")
    @Operation(summary = "Get lounge booking details by bookingId")
    public LoungeBookingEntity get(@PathVariable UUID bookingId) {
        return loungeBookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException("Lounge booking not found for bookingId: " + bookingId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create lounge booking details")
    public LoungeBookingEntity create(@Valid @RequestBody LoungeBookingCreateRequest request) {
        BookingEntity booking = bookingRepository.findById(request.bookingId())
                .orElseThrow(() -> new NotFoundException("Booking not found: " + request.bookingId()));

        LoungeBookingEntity entity = new LoungeBookingEntity();
        entity.setBookingId(booking.getId());
        entity.setLoungePassCount(request.loungePassCount());
        entity.setCreatedAt(Instant.now());
        entity.setUpdatedAt(Instant.now());

        return loungeBookingRepository.save(entity);
    }
}
