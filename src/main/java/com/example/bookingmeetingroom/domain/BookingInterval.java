package com.example.bookingmeetingroom.domain;

import java.time.LocalDateTime;

public record BookingInterval(LocalDateTime startTime, LocalDateTime endTime) {

    public BookingInterval {
        if (endTime.isBefore(startTime)) {
            throw new IllegalArgumentException("End date is before start date");
        }
        if (endTime.isEqual(startTime)) {
            throw new IllegalArgumentException("End date is equal start date");
        }
        if (!startTime.toLocalDate().isEqual(endTime.toLocalDate())){
            throw new IllegalArgumentException("Booking must start and end on the same day");
        }
    }
}