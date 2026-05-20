package com.example.bookingmeetingroom.entity;

import com.example.bookingmeetingroom.domain.BookingStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table (name = "bookings")
public class BookingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private RoomEntity room;
    @Column
    private LocalDateTime startTime;
    @Column
    private LocalDateTime endTime;
    @Column
    private BookingStatus status;
    @Column
    private String topicOfMeeting;

    public BookingEntity() {
    }

    public BookingEntity(Long id, UserEntity user, RoomEntity room, LocalDateTime startTime, LocalDateTime endTime, BookingStatus status, String topicOfMeeting) {
        this.id = id;
        this.user = user;
        this.room = room;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.topicOfMeeting = topicOfMeeting;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public RoomEntity getRoom() {
        return room;
    }

    public void setRoom(RoomEntity room) {
        this.room = room;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public String getTopicOfMeeting() {
        return topicOfMeeting;
    }

    public void setTopicOfMeeting(String topicOfMeeting) {
        this.topicOfMeeting = topicOfMeeting;
    }
}
