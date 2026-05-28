package com.example.bookingmeetingroom.entity;

import com.example.bookingmeetingroom.domain.AuditAction;
import com.example.bookingmeetingroom.domain.AuditEntityType;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Table(name = "audits")
@Entity
public class AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AuditAction action;

    @Column(nullable = false)
    private LocalDateTime time;

    @Column(nullable = false, name = "user_id")
    private Long userId;

    @Column(nullable = false, name = "entity_type")
    @Enumerated(EnumType.STRING)
    private AuditEntityType entityType;

    @Column(nullable = false, name = "entity_id")
    private Long entityId;

    public AuditEntity() {
    }

    public AuditEntity(Long id, AuditAction action, LocalDateTime time, Long userId, AuditEntityType entityType, Long entityId) {
        this.id = id;
        this.action = action;
        this.time = time;
        this.userId = userId;
        this.entityType = entityType;
        this.entityId = entityId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AuditAction getAction() {
        return action;
    }

    public void setAction(AuditAction action) {
        this.action = action;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public AuditEntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(AuditEntityType entityType) {
        this.entityType = entityType;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }
}
