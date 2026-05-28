package com.example.bookingmeetingroom.service;


import com.example.bookingmeetingroom.domain.Booking;
import com.example.bookingmeetingroom.entity.BookingEntity;
import com.example.bookingmeetingroom.entity.RoomEntity;
import com.example.bookingmeetingroom.entity.UserEntity;
import com.example.bookingmeetingroom.repository.BookingRepository;
import com.example.bookingmeetingroom.repository.RoomRepository;
import com.example.bookingmeetingroom.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import static com.example.bookingmeetingroom.domain.AuditAction.*;
import static com.example.bookingmeetingroom.domain.BookingStatus.CANCELLED;
import static com.example.bookingmeetingroom.domain.BookingStatus.CONFIRMED;


@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final AuditService auditService;
    private final Logger logger = LoggerFactory.getLogger(BookingService.class);

    public BookingService(BookingRepository bookingRepository, UserRepository userRepository, RoomRepository roomRepository, AuditService auditService) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
        this.auditService = auditService;
    }

    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id).map(this::toBooking)
                .orElseThrow(() -> new NoSuchElementException("Booking not exist by id = " + id));
    }

    @Transactional
    public void cancelBookingById(Long id) {
        BookingEntity bookingEntity = bookingRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Booking not exist by id = " + id));
        if (bookingEntity.getStatus().equals(CANCELLED)) {
            throw new IllegalStateException("Can't cancel already cancelled booking id = " + id);
        }
        bookingEntity.setStatus(CANCELLED);
        bookingRepository.save(bookingEntity);
        auditService.auditBooking(CANCEL, bookingEntity.getId());
        logger.info("Booking id = {} successfully cancelled", id);
    }

    @Transactional
    public Booking createBooking(Booking booking) {
        validateBooking(booking);

        UserEntity user = userRepository.findById(booking.userId())
                .orElseThrow(() -> new NoSuchElementException("User not exist by id = " + booking.userId()));

        RoomEntity room = roomRepository.findById(booking.roomId())
                .orElseThrow(() -> new NoSuchElementException("Room not exist by id = " + booking.roomId()));

        boolean hasOverlap = bookingRepository.findAllByRoomAndStatus(room, CONFIRMED).stream()
                .anyMatch(it -> IntervalChecker.intervalCheck(it.getBookingInterval(), booking.bookingInterval()));

        if (hasOverlap) {
            throw new IllegalStateException("The room is already booked for this time interval.");
        }

        BookingEntity bookingEntity = new BookingEntity(
                null,
                user,
                room,
                booking.bookingInterval(),
                CONFIRMED,
                booking.topicOfMeeting()
        );
        bookingRepository.save(bookingEntity);
        auditService.auditBooking(CREATE, bookingEntity.getId());
        logger.info("Booking successfully created");
        return toBooking(bookingEntity);
    }

    @Transactional
    public Booking updateBookingById(Booking booking) {
        if (booking.id() == null) {
            throw new IllegalArgumentException("Id can't be null");
        }

        validateBooking(booking);

        BookingEntity bookingEntity = bookingRepository.findById(booking.id()).orElseThrow(() -> new NoSuchElementException("Booking not exist by id = " + booking.id()));

        if (bookingEntity.getStatus().equals(CANCELLED)) {
            throw new IllegalArgumentException("Can't update cancelled booking id = " + booking.id());
        }

        if (booking.status() != null) {
            throw new IllegalArgumentException("Status should be null");
        }

        UserEntity user = userRepository.findById(booking.userId())
                .orElseThrow(() -> new NoSuchElementException("User not exist by id = " + booking.userId()));

        RoomEntity room = roomRepository.findById(booking.roomId())
                .orElseThrow(() -> new NoSuchElementException("Room not exist by id = " + booking.roomId()));

        boolean hasOverlap = bookingRepository.findAllByRoomAndStatus(room, CONFIRMED).stream()
                .filter(it -> !it.getId().equals(booking.id()))
                .anyMatch(it -> IntervalChecker.intervalCheck(booking.bookingInterval(), it.getBookingInterval()));

        if (hasOverlap) {
            throw new IllegalStateException("The room is already booked for this time interval.");
        }

        bookingEntity = new BookingEntity(
                booking.id(),
                user,
                room,
                booking.bookingInterval(),
                CONFIRMED,
                booking.topicOfMeeting()
        );
        bookingRepository.save(bookingEntity);
        auditService.auditBooking(UPDATE, booking.id());
        logger.info("Booking id = {} successfully updated", booking.id());
        return toBooking(bookingEntity);
    }

    public List<Booking> getAllActualBookings() {
        return bookingRepository.findAllByStatusAndBookingIntervalEndTimeAfter(BookingStatus.CONFIRMED, LocalDateTime.now()).stream()
                .map(this::toBooking)
                .toList();
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

    private void validateBooking(Booking booking) {
        if (booking.userId() == null) {
            throw new IllegalArgumentException("User id can't be null");
        }
        if (booking.roomId() == null) {
            throw new IllegalArgumentException("Room id can't be null");
        }
        if (booking.status() != null) {
            throw new IllegalArgumentException("Status should be null");
        }
        if (booking.bookingInterval() == null) {
            throw new IllegalArgumentException("booking interval can't be null");
        }
        if (booking.topicOfMeeting() == null) {
            throw new IllegalArgumentException("Topic of meeting can't be null");
        }
    }
}
