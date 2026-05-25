package com.example.bookingmeetingroom.service;

import com.example.bookingmeetingroom.domain.BookingStatus;
import com.example.bookingmeetingroom.domain.RoomWeeklyStatistic;
import com.example.bookingmeetingroom.entity.BookingEntity;
import com.example.bookingmeetingroom.entity.RoomEntity;
import com.example.bookingmeetingroom.repository.BookingRepository;
import com.example.bookingmeetingroom.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class RoomStatisticService {
    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final LocalDateTime startWeek;

    public RoomStatisticService(BookingRepository bookingRepository, RoomRepository roomRepository) {
        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
        startWeek = LocalDateTime.now().minusDays(7);
    }

    public RoomWeeklyStatistic getRoomWeeklyStatistic(Long roomId) {
        RoomEntity room = roomRepository.findById(roomId)
                .orElseThrow(() -> new NoSuchElementException("Room not found by id = " + roomId));

        List<BookingEntity> bookings = bookingRepository.findAllByRoomAndStatusAndBookingIntervalStartTimeAfter(roomId, BookingStatus.CONFIRMED, startWeek);

        Long totalMinutes = bookings
                .stream()
                .mapToLong(it -> Duration.between(
                        it.getBookingInterval().startTime(),
                        it.getBookingInterval().endTime()
                ).toMinutes())
                .sum();

        long activeDaysCount = bookings.stream()
                .map(it -> it.getBookingInterval().startTime().toLocalDate())
                .distinct()
                .count();

        long averageMinutesPerDay = activeDaysCount > 0 ? Math.round((double) totalMinutes / activeDaysCount) : 0;

        return new RoomWeeklyStatistic(
                room.getName(),
                bookings.size(),
                activeDaysCount,
                averageMinutesPerDay
        );
    }

    public List<RoomWeeklyStatistic> getAllRoomsStatistics() {
        return roomRepository.findAll().stream()
                .sorted(Comparator.comparing(RoomEntity::getId))
                .map(it -> getRoomWeeklyStatistic(it.getId()))
                .toList();
    }

}
