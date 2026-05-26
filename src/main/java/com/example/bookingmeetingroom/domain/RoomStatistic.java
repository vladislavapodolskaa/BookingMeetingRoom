package com.example.bookingmeetingroom.domain;

public record RoomStatistic(
        String roomName,
        int totalBookings,
        long activeDaysCount,
        long averageMinutesPerDay
) {
}
