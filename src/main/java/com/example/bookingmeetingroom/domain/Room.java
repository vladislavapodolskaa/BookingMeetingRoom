package com.example.bookingmeetingroom.domain;

public record Room(
        Long id,
        String name,
        int capacity
) {
}
