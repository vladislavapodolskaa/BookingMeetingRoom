package com.example.bookingmeetingroom.domain;

public record BookingEvent(
        AuditAction action,
        Long entityId
) {
}
