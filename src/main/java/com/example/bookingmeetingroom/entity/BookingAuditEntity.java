package com.example.bookingmeetingroom.entity;

import com.example.bookingmeetingroom.domain.AuditAction;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Table(name = "audits")
@Entity
public class BookingAuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private AuditAction action;

    @Column(nullable = false)
    private LocalDateTime time;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private BookingEntity booking;

    public BookingAuditEntity(Long id, AuditAction action, LocalDateTime time, UserEntity user, BookingEntity booking) {
        this.id = id;
        this.action = action;
        this.time = time;
        this.user = user;
        this.booking = booking;
    }

    public BookingAuditEntity() {
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

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public BookingEntity getBooking() {
        return booking;
    }

    public void setBooking(BookingEntity booking) {
        this.booking = booking;
    }
}
