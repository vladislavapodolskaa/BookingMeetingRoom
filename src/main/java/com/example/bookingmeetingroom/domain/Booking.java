package com.example.bookingmeetingroom.domain;

public record Booking(
        Long id,
        Long userId,
        Long roomId,
        BookingInterval bookingInterval,
        BookingStatus status,
        String topicOfMeeting
) {
}
