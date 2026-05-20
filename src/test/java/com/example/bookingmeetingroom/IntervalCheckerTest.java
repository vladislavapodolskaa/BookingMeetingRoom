package com.example.bookingmeetingroom;
import com.example.bookingmeetingroom.Service.IntervalChecker;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class IntervalCheckerTest {
    @Test
    void shouldReturnFalseWhenIntervalsNotIntersect() {
        LocalDateTime start1 = LocalDateTime.of(2026, 5, 18, 10, 0);
        LocalDateTime end1 = LocalDateTime.of(2026, 5, 18, 12, 0);

        LocalDateTime start2 = LocalDateTime.of(2026, 5, 18, 12, 30);
        LocalDateTime end2 = LocalDateTime.of(2026, 5, 18, 13, 0);

        IntervalChecker checker = new IntervalChecker(start1, end1, start2, end2);

        boolean result = checker.isIntervalsIntersect();

        assertFalse(result, "Интервалы не должны пересекаться");
    }
    @Test
    void shouldReturnFalseWhenIntervalsBorder() {
        LocalDateTime start1 = LocalDateTime.of(2026, 5, 18, 10, 0);
        LocalDateTime end1 = LocalDateTime.of(2026, 5, 18, 12, 0);

        LocalDateTime start2 = LocalDateTime.of(2026, 5, 18, 12, 0);
        LocalDateTime end2 = LocalDateTime.of(2026, 5, 18, 15, 0);

        IntervalChecker checker = new IntervalChecker(start1, end1, start2, end2);

        boolean result = checker.isIntervalsIntersect();

        assertFalse(result, "Интервалы не должны пересекаться");
    }
    @Test
    void shouldReturnTrueWhenIntervalsIntersect() {
        LocalDateTime start1 = LocalDateTime.of(2026, 5, 18, 10, 0);
        LocalDateTime end1 = LocalDateTime.of(2026, 5, 18, 12, 0);

        LocalDateTime start2 = LocalDateTime.of(2026, 5, 18, 9, 0);
        LocalDateTime end2 = LocalDateTime.of(2026, 5, 18, 11, 0);

        IntervalChecker checker = new IntervalChecker(start1, end1, start2, end2);

        boolean result = checker.isIntervalsIntersect();

        assertTrue(result, "Интервалы [10-12] и [11-13] должны пересекаться");
    }
    @Test
    void shouldReturnExeptionWhenInvertedInterval(){
        LocalDateTime start1 = LocalDateTime.of(2026, 5, 18, 12, 0);
        LocalDateTime end1 = LocalDateTime.of(2026, 5, 18, 10, 0);

        LocalDateTime start2 = LocalDateTime.of(2026, 5, 18, 11, 0);
        LocalDateTime end2 = LocalDateTime.of(2026, 5, 18, 13, 0);

        IntervalChecker checker = new IntervalChecker(start1, end1, start2, end2);


        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            checker.isIntervalsIntersect();
        });

        assertEquals("End date is before start date at the first date", exception.getMessage());
    }

    @Test
    void shouldReturnExeptionWhenZeroInterval(){
        LocalDateTime start1 = LocalDateTime.of(2026, 5, 18, 12, 0);
        LocalDateTime end1 = LocalDateTime.of(2026, 5, 18, 12, 0);

        LocalDateTime start2 = LocalDateTime.of(2026, 5, 18, 11, 0);
        LocalDateTime end2 = LocalDateTime.of(2026, 5, 18, 13, 0);

        IntervalChecker checker = new IntervalChecker(start1, end1, start2, end2);


        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            checker.isIntervalsIntersect();
        });

        assertEquals("End date is equal start date at the first date", exception.getMessage());
    }
}
