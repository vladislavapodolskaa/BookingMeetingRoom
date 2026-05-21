package com.example.bookingmeetingroom.service;

import com.example.bookingmeetingroom.domain.BookingInterval;


public class IntervalChecker {
    private IntervalChecker() {
    }

    public static boolean intervalCheck(BookingInterval dateFirst, BookingInterval dateSecond) {
        return dateSecond.endTime().isAfter(dateFirst.startTime()) && dateSecond.startTime().isBefore(dateFirst.endTime());
    }
}
