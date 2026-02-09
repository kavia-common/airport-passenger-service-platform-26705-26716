package com.example.airportfacilityservice.api;

import com.example.airportfacilityservice.api.dto.HotelBookingCreateRequest;
import com.example.airportfacilityservice.api.error.NotFoundException;
import com.example.airportfacilityservice.domain.BookingEntity;
import com.example.airportfacilityservice.domain.HotelBookingEntity;
import com.example.airportfacilityservice.repo.BookingRepository;
import com.example.airportfacilityservice.repo.HotelBookingRepository;
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
@RequestMapping("/api/hotel-bookings")
@Tag(name = "Hotel Bookings", description = "Hotel-specific booking details")
public class HotelBookingController {

    private final BookingRepository bookingRepository;
    private final HotelBookingRepository hotelBookingRepository;

    public HotelBookingController(BookingRepository bookingRepository, HotelBookingRepository hotelBookingRepository) {
        this.bookingRepository = bookingRepository;
        this.hotelBookingRepository = hotelBookingRepository;
    }

    @GetMapping("/{bookingId}")
    @Operation(summary = "Get hotel booking details by bookingId")
    public HotelBookingEntity get(@PathVariable UUID bookingId) {
        return hotelBookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException("Hotel booking not found for bookingId: " + bookingId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create hotel booking details")
    public HotelBookingEntity create(@Valid @RequestBody HotelBookingCreateRequest request) {
        BookingEntity booking = bookingRepository.findById(request.bookingId())
                .orElseThrow(() -> new NotFoundException("Booking not found: " + request.bookingId()));

        HotelBookingEntity entity = new HotelBookingEntity();
        entity.setBookingId(booking.getId());
        entity.setGuestsCount(request.guestsCount());
        entity.setRoomType(request.roomType());
        entity.setCreatedAt(Instant.now());
        entity.setUpdatedAt(Instant.now());

        return hotelBookingRepository.save(entity);
    }
}
