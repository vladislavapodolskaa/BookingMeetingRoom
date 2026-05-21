package com.example.bookingmeetingroom.service;

import com.example.bookingmeetingroom.domain.AuditAction;
import com.example.bookingmeetingroom.domain.Booking;
import com.example.bookingmeetingroom.domain.BookingAudit;
import com.example.bookingmeetingroom.entity.BookingAuditEntity;
import com.example.bookingmeetingroom.entity.BookingEntity;
import com.example.bookingmeetingroom.repository.BookingAuditRepository;
import com.example.bookingmeetingroom.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BookingAuditService {
    private final BookingAuditRepository bookingAuditRepository;
    private final UserRepository userRepository;

    public BookingAuditService(BookingAuditRepository bookingAuditRepository, UserRepository userRepository) {
        this.bookingAuditRepository = bookingAuditRepository;
        this.userRepository = userRepository;
    }

    public List<BookingAudit> getBookingAuditsByBookingId(Long id) {
        return bookingAuditRepository.findByBookingId(id).stream().map(this::toBookingAudit).toList();
    }

    public List<BookingAudit> getBookingAuditsByRoomId(Long id) {

        return bookingAuditRepository.findByRoomId(id).stream().map(this::toBookingAudit).toList();
    }

    public List<BookingAudit> findAll() {
        return bookingAuditRepository.findAll().stream().map(this::toBookingAudit).toList();
    }

    public List<BookingAudit> getBookingAuditsByUserId(Long id) {
        return bookingAuditRepository.findByUserId(id).stream().map(this::toBookingAudit).toList();
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
    private Booking toBooking(BookingEntity bookingEntity){
        return new Booking(
                bookingEntity.getId(),
                bookingEntity.getUser().getId(),
                bookingEntity.getRoom().getId(),
                bookingEntity.getStartTime(),
                bookingEntity.getEndTime(),
                bookingEntity.getStatus(),
                bookingEntity.getTopicOfMeeting()
        );
    }

    public BookingAudit cancelBookingAudit(Long userId, BookingEntity booking) {
        BookingAuditEntity bookingAuditEntity = new BookingAuditEntity(
                null,
                AuditAction.CANCEL,
                LocalDateTime.now(),
                userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found id = " + userId)),
                booking
        );
        bookingAuditRepository.save(bookingAuditEntity);
        return toBookingAudit(bookingAuditEntity);
    }

    public BookingAudit createBookingAudit(BookingEntity bookingEntity) {
        BookingAuditEntity bookingAuditEntity = new BookingAuditEntity(
                null,
                AuditAction.CREATE,
                LocalDateTime.now(),
                bookingEntity.getUser(),
                bookingEntity
        );
        bookingAuditRepository.save(bookingAuditEntity);
        return toBookingAudit(bookingAuditEntity);
    }
    public BookingAudit updateBookingAudit(BookingEntity bookingEntity) {
        BookingAuditEntity bookingAuditEntity = new BookingAuditEntity(
                null,
                AuditAction.UPDATE,
                LocalDateTime.now(),
                bookingEntity.getUser(),
                bookingEntity
        );
        bookingAuditRepository.save(bookingAuditEntity);
        return toBookingAudit(bookingAuditEntity);
    }
}
