package com.example.bookingmeetingroom.service;


import com.example.bookingmeetingroom.domain.Booking;
import com.example.bookingmeetingroom.domain.BookingInterval;
import com.example.bookingmeetingroom.domain.BookingStatus;
import com.example.bookingmeetingroom.entity.BookingEntity;
import com.example.bookingmeetingroom.entity.RoomEntity;
import com.example.bookingmeetingroom.entity.UserEntity;
import com.example.bookingmeetingroom.repository.BookingRepository;
import com.example.bookingmeetingroom.repository.RoomRepository;
import com.example.bookingmeetingroom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;


@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;

    @Autowired
    public BookingService(BookingRepository bookingRepository, UserRepository userRepository, RoomRepository roomRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
    }

    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id).map(this::toBooking)
                .orElseThrow(() -> new NoSuchElementException("Booking not exist by id = " + id));
    }

    public void cancelBookingById(Long id) {
        BookingEntity bookingEntity = bookingRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Booking not exist by id = " + id));
        bookingEntity.setStatus(BookingStatus.CANCELLED);
        bookingRepository.save(bookingEntity);
    }

    public Booking createBooking(Booking booking) {
        if (booking.id() != null){
            throw new IllegalArgumentException("Id should be null");
        }
        if (booking.status() != null){
            throw new IllegalArgumentException("Status should be null");
        }
        BookingInterval interval = new BookingInterval(booking.startTime(), booking.endTime());

        UserEntity user = userRepository.findById(booking.userId())
                .orElseThrow(()-> new NoSuchElementException("User not exist by id = " + booking.userId()));

        RoomEntity room = roomRepository.findById(booking.roomId())
                .orElseThrow(() -> new NoSuchElementException("Room not exist by id = " + booking.roomId()));

        boolean hasOverlap = bookingRepository.findAllByRoomAndStatus(room, BookingStatus.CONFIRMED).stream()
                .anyMatch(it -> IntervalChecker.intervalCheck(new BookingInterval(it.getStartTime(), it.getEndTime()), interval));

         if (hasOverlap){
             throw new IllegalStateException("The room is already booked for this time interval.");
         }

        BookingEntity bookingEntity = new BookingEntity(
                null,
                user,
                room,
                booking.startTime(),
                booking.endTime(),
                BookingStatus.CONFIRMED,
                booking.topicOfMeeting()
                );
        bookingRepository.save(bookingEntity);
        return toBooking(bookingEntity);
    }

    public Booking updateBookingById(Long id, Booking booking){
        BookingEntity bookingEntity = bookingRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Booking not exist by id = " + id));
        if (bookingEntity.getStatus().equals(BookingStatus.CANCELLED)){
            throw new IllegalArgumentException("Can't update cancelled booking id = " + id);
        }
        if (booking.id() != null){
            throw new IllegalArgumentException("Id should be null");
        }
        if (booking.status() != null){
            throw new IllegalArgumentException("Status should be null");
        }
        BookingInterval interval = new BookingInterval(booking.startTime(), booking.endTime());

        UserEntity user = userRepository.findById(booking.userId())
                .orElseThrow(()-> new NoSuchElementException("User not exist by id = " + booking.userId()));

        RoomEntity room = roomRepository.findById(booking.roomId())
                .orElseThrow(() -> new NoSuchElementException("Room not exist by id = " + booking.roomId()));

        boolean hasOverlap = bookingRepository.findAllByRoomAndStatus(room, BookingStatus.CONFIRMED).stream()
                .filter(it -> !it.getId().equals(id))
                .anyMatch(it -> IntervalChecker.intervalCheck(new BookingInterval(it.getStartTime(), it.getEndTime()), interval));

        if (hasOverlap){
            throw new IllegalStateException("The room is already booked for this time interval.");
        }

        bookingEntity = new BookingEntity(
                id,
                user,
                room,
                booking.startTime(),
                booking.endTime(),
                BookingStatus.CONFIRMED,
                booking.topicOfMeeting()
        );
        bookingRepository.save(bookingEntity);
        return toBooking(bookingEntity);
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

    public List<Booking> getAllBooking() {
        return bookingRepository.findAll().stream()
                .map(this::toBooking)
                .toList();
    }
}
