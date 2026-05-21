package com.example.bookingmeetingroom;
import com.example.bookingmeetingroom.domain.BookingInterval;
import com.example.bookingmeetingroom.service.IntervalChecker;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class IntervalCheckerTest {
    @Test
    void shouldReturnFalseWhenIntervalsNotIntersect() {
        LocalDateTime start1 = LocalDateTime.of(2026, 5, 18, 10, 0);
        LocalDateTime end1 = LocalDateTime.of(2026, 5, 18, 12, 0);
        BookingInterval dateFirst = new BookingInterval(start1, end1);

        LocalDateTime start2 = LocalDateTime.of(2026, 5, 18, 12, 30);
        LocalDateTime end2 = LocalDateTime.of(2026, 5, 18, 13, 0);
        BookingInterval dateSecond = new BookingInterval(start2, end2);
        boolean result = IntervalChecker.intervalCheck(dateFirst, dateSecond);

        assertFalse(result, "Интервалы не должны пересекаться");
    }
    @Test
    void shouldReturnFalseWhenIntervalsBorder() {
        LocalDateTime start1 = LocalDateTime.of(2026, 5, 18, 10, 0);
        LocalDateTime end1 = LocalDateTime.of(2026, 5, 18, 12, 0);
        BookingInterval dateFirst = new BookingInterval(start1, end1);

        LocalDateTime start2 = LocalDateTime.of(2026, 5, 18, 12, 0);
        LocalDateTime end2 = LocalDateTime.of(2026, 5, 18, 15, 0);
        BookingInterval dateSecond = new BookingInterval(start2, end2);

        boolean result = IntervalChecker.intervalCheck(dateFirst, dateSecond);

        assertFalse(result, "Интервалы не должны пересекаться");
    }
    @Test
    void shouldReturnTrueWhenIntervalsIntersect() {
        LocalDateTime start1 = LocalDateTime.of(2026, 5, 18, 10, 0);
        LocalDateTime end1 = LocalDateTime.of(2026, 5, 18, 12, 0);
        BookingInterval dateFirst = new BookingInterval(start1, end1);

        LocalDateTime start2 = LocalDateTime.of(2026, 5, 18, 9, 0);
        LocalDateTime end2 = LocalDateTime.of(2026, 5, 18, 11, 0);
        BookingInterval dateSecond = new BookingInterval(start2, end2);

        boolean result = IntervalChecker.intervalCheck(dateFirst, dateSecond);

        assertTrue(result, "Интервалы [10-12] и [11-13] должны пересекаться");
    }
    @Test
    void shouldReturnExeptionWhenInvertedInterval(){
        LocalDateTime start1 = LocalDateTime.of(2026, 5, 18, 12, 0);
        LocalDateTime end1 = LocalDateTime.of(2026, 5, 18, 10, 0);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new BookingInterval(start1, end1));
        assertEquals("End date is before start date", exception.getMessage());
    }

    @Test
    void shouldReturnExeptionWhenZeroInterval(){
        LocalDateTime start1 = LocalDateTime.of(2026, 5, 18, 12, 0);
        LocalDateTime end1 = LocalDateTime.of(2026, 5, 18, 12, 0);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new BookingInterval(start1, end1));

        assertEquals("End date is equal start date", exception.getMessage());
    }
}
