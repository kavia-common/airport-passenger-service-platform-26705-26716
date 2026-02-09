package com.example.airportfacilityservice.api;

import com.example.airportfacilityservice.api.dto.ParkingBookingCreateRequest;
import com.example.airportfacilityservice.api.error.NotFoundException;
import com.example.airportfacilityservice.domain.BookingEntity;
import com.example.airportfacilityservice.domain.ParkingBookingEntity;
import com.example.airportfacilityservice.repo.BookingRepository;
import com.example.airportfacilityservice.repo.ParkingBookingRepository;
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
@RequestMapping("/api/parking-bookings")
@Tag(name = "Parking Bookings", description = "Parking-specific booking details")
public class ParkingBookingController {

    private final BookingRepository bookingRepository;
    private final ParkingBookingRepository parkingBookingRepository;

    public ParkingBookingController(BookingRepository bookingRepository, ParkingBookingRepository parkingBookingRepository) {
        this.bookingRepository = bookingRepository;
        this.parkingBookingRepository = parkingBookingRepository;
    }

    @GetMapping("/{bookingId}")
    @Operation(summary = "Get parking booking details by bookingId")
    public ParkingBookingEntity get(@PathVariable UUID bookingId) {
        return parkingBookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException("Parking booking not found for bookingId: " + bookingId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create parking booking details")
    public ParkingBookingEntity create(@Valid @RequestBody ParkingBookingCreateRequest request) {
        BookingEntity booking = bookingRepository.findById(request.bookingId())
                .orElseThrow(() -> new NotFoundException("Booking not found: " + request.bookingId()));

        ParkingBookingEntity entity = new ParkingBookingEntity();
        entity.setBookingId(booking.getId());
        entity.setVehicleNumber(request.vehicleNumber());
        entity.setSlotCode(request.slotCode());
        entity.setCreatedAt(Instant.now());
        entity.setUpdatedAt(Instant.now());

        return parkingBookingRepository.save(entity);
    }
}
