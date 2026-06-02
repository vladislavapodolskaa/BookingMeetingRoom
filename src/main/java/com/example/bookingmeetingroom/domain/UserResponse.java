package com.example.bookingmeetingroom.domain;

public record UserResponse(
        long id,
        String name,
        String email,
        String department,
        String login
) {
}
