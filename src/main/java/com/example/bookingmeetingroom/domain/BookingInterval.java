package com.example.bookingmeetingroom.domain;

import java.time.LocalDateTime;

public class BookingInterval {
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;

    public BookingInterval(LocalDateTime startTime, LocalDateTime endTime) {
        validationInterval(startTime, endTime);
        this.startTime = startTime;
        this.endTime = endTime;
    }
    private void validationInterval(LocalDateTime startTime, LocalDateTime endTime){
        if (endTime.isBefore(startTime)){
            throw new IllegalArgumentException("End date is before start date");
        }
        if (endTime.isEqual(startTime)){
            throw new IllegalArgumentException("End date is equal start date");
        }
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }
}
