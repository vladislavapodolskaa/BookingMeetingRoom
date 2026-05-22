package com.example.bookingmeetingroom.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.time.LocalDateTime;

@Embeddable
public record BookingInterval(
        @Column(name = "start_time") LocalDateTime startTime,
        @Column(name = "end_time") LocalDateTime endTime
) {

    public BookingInterval {
        if (endTime == null || startTime == null){
            throw new NullPointerException("Date can't be null");
        }
        if (endTime.isBefore(startTime)) {
            throw new IllegalArgumentException("End date is before start date");
        }
        if (endTime.isEqual(startTime)) {
            throw new IllegalArgumentException("End date is equal start date");
        }
        if (!startTime.toLocalDate().isEqual(endTime.toLocalDate())) {
            throw new IllegalArgumentException("Booking must start and end on the same day");
        }
    }
}