package com.example.bookingmeetingroom.domain;

public record RoomWeeklyStatistic(
        String roomName,
        int totalBookings,
        long activeDaysCount,
        long averageMinutesPerDay
) {
}
