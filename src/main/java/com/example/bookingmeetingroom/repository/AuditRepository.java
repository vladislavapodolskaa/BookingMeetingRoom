package com.example.bookingmeetingroom.repository;

import com.example.bookingmeetingroom.domain.AuditEntityType;
import com.example.bookingmeetingroom.entity.AuditEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AuditRepository extends JpaRepository<AuditEntity, Long> {

    List<AuditEntity> findByEntityTypeAndEntityId(AuditEntityType auditEntityType, Long entityId);

    @Query("""
            select a from AuditEntity a
            join BookingEntity b on a.entityId = b.id
            where a.entityType = "BOOKING"
            and b.room.id = :roomId
            """)
    List<AuditEntity> findBookingAuditByRoomId(@Param("roomId") Long roomId);

    List<AuditEntity> findAllByEntityTypeAndUserId(AuditEntityType entityType, Long userId);

    List<AuditEntity> findAllByEntityType(AuditEntityType entityType);
}
