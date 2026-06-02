package com.example.bookingmeetingroom.domain;

public record User(
        Long id,
        String name,
        String email,
        String department,
        String login,
        String password,
        UserRole role,
        UserStatus status
) {
}
