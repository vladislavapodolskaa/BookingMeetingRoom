package com.example.bookingmeetingroom.repository;

import com.example.bookingmeetingroom.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoomRepository extends JpaRepository<RoomEntity, Long> {
}
