package com.example.bookingmeetingroom.repository;

import com.example.bookingmeetingroom.entity.BookingAuditEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookingAuditRepository extends JpaRepository<BookingAuditEntity, Long> {

    List<BookingAuditEntity> findByBookingId(Long bookingId);

    @Query("select b from BookingAuditEntity b where b.booking.room.id = :roomId")
    List<BookingAuditEntity> findByRoomId(@Param("roomId") Long roomId);

    List<BookingAuditEntity> findByUserId(Long userId);
}
