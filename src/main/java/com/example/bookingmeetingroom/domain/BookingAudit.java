package com.example.bookingmeetingroom.domain;

import java.time.LocalDateTime;

public record BookingAudit(
        Long id,
        AuditAction action,
        LocalDateTime time,
        Long userId,
        Booking booking
) {
}
