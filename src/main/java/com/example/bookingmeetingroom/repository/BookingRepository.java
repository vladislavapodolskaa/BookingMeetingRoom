package com.example.bookingmeetingroom.repository;

import com.example.bookingmeetingroom.domain.BookingStatus;
import com.example.bookingmeetingroom.entity.BookingEntity;
import com.example.bookingmeetingroom.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookingRepository extends JpaRepository<BookingEntity, Long> {

    List<BookingEntity> findAllByRoomAndStatus(RoomEntity room, BookingStatus status);

    @Query("select b from BookingEntity b where b.status = :status and b.bookingInterval.endTime >= :endTime")
    List<BookingEntity> findAllByStatusAndBookingInterval_EndTimeAfter(@Param("status") BookingStatus status, @Param("endTime") LocalDateTime endTime);

    @Query("select b from BookingEntity b where b.room.id = :roomId " +
            "and b.status = :status " +
            "and b.bookingInterval.startTime > :date")
    List<BookingEntity> findAllByRoomAndStatusAndBookingIntervalStartTimeAfter(
            @Param("roomId") Long roomId,
            @Param("status") BookingStatus status,
            @Param("date") LocalDateTime date
    );

    @Query("select b from BookingEntity b where b.status = :status " +
            "and b.bookingInterval.startTime > :date")
    List<BookingEntity> findAllByStatusAndBookingIntervalStartTimeAfter(
            @Param("status") BookingStatus status,
            @Param("date") LocalDateTime date
    );
}
