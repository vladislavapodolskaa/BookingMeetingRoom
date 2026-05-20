package com.example.bookingmeetingroom.Domain;

public record Room(
        Long id,
        String name,
        int capacity
) {
}
