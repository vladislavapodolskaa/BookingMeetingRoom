package com.example.bookingmeetingroom.domain;

import java.time.LocalDateTime;


// TODO это DTO для клиента и это хорошо, предлагаю здесь использовать BookingInterval
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
