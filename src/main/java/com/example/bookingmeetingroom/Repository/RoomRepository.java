package com.example.bookingmeetingroom.Repository;

import com.example.bookingmeetingroom.Domain.Room;
import com.example.bookingmeetingroom.Entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface RoomRepository extends JpaRepository<RoomEntity, Long> {
}
