package com.example.bookingmeetingroom.entity;

import com.example.bookingmeetingroom.domain.BookingInterval;
import com.example.bookingmeetingroom.domain.BookingStatus;
import jakarta.persistence.*;

@Entity
@Table(name = "bookings")
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
    @Embedded
    private BookingInterval bookingInterval;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus status;
    @Column(nullable = false)
    private String topicOfMeeting;

    public BookingEntity() {
    }

    public BookingEntity(Long id, UserEntity user, RoomEntity room, BookingInterval bookingInterval, BookingStatus status, String topicOfMeeting) {
        this.id = id;
        this.user = user;
        this.room = room;
        this.bookingInterval = bookingInterval;
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

    public BookingInterval getBookingInterval() {
        return bookingInterval;
    }

    public void setBookingInterval(BookingInterval bookingInterval) {
        this.bookingInterval = bookingInterval;
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
