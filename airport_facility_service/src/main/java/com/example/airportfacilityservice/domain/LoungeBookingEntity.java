package com.example.airportfacilityservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;

/**
 * Lounge-specific booking details (1:1 with bookings row).
 */
@Entity
@Table(name = "lounge_bookings")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class LoungeBookingEntity {

    @Id
    @Column(name = "booking_id")
    private UUID bookingId;

    @Column(name = "lounge_pass_count", nullable = false)
    private int loungePassCount = 1;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    public UUID getBookingId() {
        return bookingId;
    }

    public void setBookingId(UUID bookingId) {
        this.bookingId = bookingId;
    }

    public int getLoungePassCount() {
        return loungePassCount;
    }

    public void setLoungePassCount(int loungePassCount) {
        this.loungePassCount = loungePassCount;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
