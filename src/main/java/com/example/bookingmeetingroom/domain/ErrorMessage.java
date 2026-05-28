package com.example.bookingmeetingroom.domain;

import java.time.LocalDateTime;

public record ErrorMessage(
        int status,
        LocalDateTime time,
        String message,
        String description
) {
}
