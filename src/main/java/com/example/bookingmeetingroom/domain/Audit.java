package com.example.bookingmeetingroom.domain;

import java.time.LocalDateTime;

public record Audit(
        Long id,
        AuditAction action,
        LocalDateTime time,
        Long userId,
        AuditEntityType entityType,
        Long entityId
) {
}
