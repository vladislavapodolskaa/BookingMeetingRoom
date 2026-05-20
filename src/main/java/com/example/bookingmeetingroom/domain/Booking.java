package com.example.bookingmeetingroom.domain;

import java.time.LocalDateTime;

public record Booking(
    Long id,
    Long userId,
    Long roomId,
    LocalDateTime startTime,
    LocalDateTime endTime,
    BookingStatus status,
    String topicOfMeeting
) {
}
