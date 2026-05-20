package com.example.bookingmeetingroom.Service;

import java.time.LocalDateTime;

public class IntervalChecker {
    private LocalDateTime startTimeFirst;
    private LocalDateTime endTimeFirst;
    private LocalDateTime startTimeSecond;
    private LocalDateTime endTimeSecond;

    public IntervalChecker(LocalDateTime startTimeFirst, LocalDateTime endTimeFirst, LocalDateTime startTimeSecond, LocalDateTime endTimeSecond) {
        this.startTimeFirst = startTimeFirst;
        this.endTimeFirst = endTimeFirst;
        this.startTimeSecond = startTimeSecond;
        this.endTimeSecond = endTimeSecond;
    }

    public boolean isIntervalsIntersect(){
        checkDates();
        return endTimeSecond.isAfter(startTimeFirst) && startTimeSecond.isBefore(endTimeFirst);
    }
    private void checkDates (){
        if (endTimeFirst.isBefore(startTimeFirst)){
            throw new IllegalArgumentException("End date is before start date at the first date");
        }
        if (endTimeSecond.isBefore(startTimeSecond)){
            throw new IllegalArgumentException("End date is before start date at the second date");
        }
        if (endTimeFirst.isEqual(startTimeFirst)){
            throw new IllegalArgumentException("End date is equal start date at the first date");
        }
        if (endTimeSecond.isEqual(startTimeSecond)){
            throw new IllegalArgumentException("End date is equal start date at the second date");
        }
    }
}
