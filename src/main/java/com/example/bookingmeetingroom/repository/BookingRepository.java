package com.example.bookingmeetingroom.repository;

import com.example.bookingmeetingroom.domain.BookingStatus;
import com.example.bookingmeetingroom.entity.BookingEntity;
import com.example.bookingmeetingroom.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<BookingEntity, Long> {

    List<BookingEntity> findAllByRoomAndStatus(RoomEntity room, BookingStatus status);

}
