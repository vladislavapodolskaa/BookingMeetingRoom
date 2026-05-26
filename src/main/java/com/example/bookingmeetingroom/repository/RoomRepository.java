package com.example.bookingmeetingroom.repository;

import com.example.bookingmeetingroom.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface RoomRepository extends JpaRepository<RoomEntity, Long> {

    @Query("select count(r) = 0 from RoomEntity r where r.id = :id")
    boolean notExistsById(@Param("id") Long id);

}
