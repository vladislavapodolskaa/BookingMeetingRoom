package com.example.bookingmeetingroom.service;

import com.example.bookingmeetingroom.domain.AuditAction;
import com.example.bookingmeetingroom.domain.Booking;
import com.example.bookingmeetingroom.domain.BookingAudit;
import com.example.bookingmeetingroom.entity.BookingAuditEntity;
import com.example.bookingmeetingroom.entity.BookingEntity;
import com.example.bookingmeetingroom.entity.UserEntity;
import com.example.bookingmeetingroom.repository.BookingAuditRepository;
import com.example.bookingmeetingroom.repository.BookingRepository;
import com.example.bookingmeetingroom.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BookingAuditService {
    private final BookingAuditRepository bookingAuditRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;

    public BookingAuditService(BookingAuditRepository bookingAuditRepository, UserRepository userRepository, BookingRepository bookingRepository) {
        this.bookingAuditRepository = bookingAuditRepository;
        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
    }

    public List<BookingAudit> getBookingAuditsByBookingId(Long id) {
        return bookingAuditRepository.findByBookingId(id).stream().map(this::toBookingAudit).toList();
    }

    public List<BookingAudit> getBookingAuditsByRoomId(Long id) {
        return bookingAuditRepository.findByRoomId(id).stream().map(this::toBookingAudit).toList();
    }

    public List<BookingAudit> findAllBookingAudits() {
        return bookingAuditRepository.findAll().stream().map(this::toBookingAudit).toList();
    }

    public List<BookingAudit> getBookingAuditsByUserId(Long id) {
        return bookingAuditRepository.findByUserId(id).stream().map(this::toBookingAudit).toList();
    }


    public BookingAudit createBookingAudit(Booking booking, AuditAction action) {
        UserEntity userEntity = userRepository.findById(booking.userId())
                .orElseThrow(() -> new NoSuchElementException("User not found by id = " + booking.userId()));
        BookingEntity bookingEntity = bookingRepository.findById(booking.id())
                .orElseThrow(() -> new NoSuchElementException("Booking not found by id = " + booking.id()));

        BookingAuditEntity bookingAuditEntity = new BookingAuditEntity(
                null,
                action,
                LocalDateTime.now(),
                userEntity,
                bookingEntity
        );
        bookingAuditRepository.save(bookingAuditEntity);
        return toBookingAudit(bookingAuditEntity);
    }

    private BookingAudit toBookingAudit(BookingAuditEntity bookingAuditEntity) {
        BookingEntity bookingEntity = bookingAuditEntity.getBooking();
        Booking domainBooking = toBooking(bookingEntity);
        return new BookingAudit(
                bookingAuditEntity.getId(),
                bookingAuditEntity.getAction(),
                bookingAuditEntity.getTime(),
                bookingAuditEntity.getUser().getId(),
                domainBooking
        );
    }

    private Booking toBooking(BookingEntity bookingEntity) {
        return new Booking(
                bookingEntity.getId(),
                bookingEntity.getUser().getId(),
                bookingEntity.getRoom().getId(),
                bookingEntity.getBookingInterval(),
                bookingEntity.getStatus(),
                bookingEntity.getTopicOfMeeting()
        );
    }
}