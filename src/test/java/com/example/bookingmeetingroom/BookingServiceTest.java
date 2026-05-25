package com.example.bookingmeetingroom;

import com.example.bookingmeetingroom.domain.Booking;
import com.example.bookingmeetingroom.domain.BookingInterval;
import com.example.bookingmeetingroom.domain.BookingStatus;
import com.example.bookingmeetingroom.entity.BookingEntity;
import com.example.bookingmeetingroom.entity.RoomEntity;
import com.example.bookingmeetingroom.entity.UserEntity;
import com.example.bookingmeetingroom.repository.BookingRepository;
import com.example.bookingmeetingroom.repository.RoomRepository;
import com.example.bookingmeetingroom.repository.UserRepository;
import com.example.bookingmeetingroom.service.BookingAuditService;
import com.example.bookingmeetingroom.service.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {
    @Mock
    private BookingRepository bookingRepository;
    @InjectMocks
    private BookingService bookingService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoomRepository roomRepository;
    @Mock
    private BookingAuditService bookingAuditService;

    private RoomEntity testRoom;

    @BeforeEach
    void setUp() {
        testRoom = new RoomEntity();
        testRoom.setId(1L);
        when(roomRepository.findById(1L)).thenReturn(Optional.of(testRoom));

        UserEntity mockUser = new UserEntity();
        mockUser.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
    }

    @Test
    void shouldThrowIllegalStateExceptionWhenBookingOverlaps() {
        BookingEntity confirmedBooking = new BookingEntity();
        confirmedBooking.setRoom(testRoom);
        confirmedBooking.setBookingInterval(new BookingInterval(LocalDateTime.of(2026, 5, 25, 12, 0),
                LocalDateTime.of(2026, 5, 25, 14, 0)));
        confirmedBooking.setStatus(BookingStatus.CONFIRMED);

        Booking newBooking = new Booking(
                null,
                1L,
                1L,
                new BookingInterval(LocalDateTime.of(2026, 5, 25, 13, 0),
                        LocalDateTime.of(2026, 5, 25, 15, 0)),
                null,
                "topic"
        );

        when(bookingRepository.findAllByRoomAndStatus(testRoom, BookingStatus.CONFIRMED)).thenReturn(List.of(confirmedBooking));

        assertThrows(IllegalStateException.class, () -> {
            bookingService.createBooking(newBooking);
        });
    }

    @Test
    void shouldCreateBookingSuccessfullyWhenBookingIsValid() {
        BookingEntity confirmedBooking = new BookingEntity();
        confirmedBooking.setRoom(testRoom);
        confirmedBooking.setBookingInterval(new BookingInterval(LocalDateTime.of(2026, 5, 25, 12, 0),
                LocalDateTime.of(2026, 5, 25, 14, 0)));
        confirmedBooking.setStatus(BookingStatus.CONFIRMED);

        Booking newBooking = new Booking(
                null,
                1L,
                1L,
                new BookingInterval(LocalDateTime.of(2026, 5, 25, 14, 0),
                        LocalDateTime.of(2026, 5, 25, 15, 0)),
                null,
                "topic"
        );

        when(bookingRepository.findAllByRoomAndStatus(testRoom, BookingStatus.CONFIRMED)).thenReturn(List.of(confirmedBooking));
        Booking savedBooking = bookingService.createBooking(newBooking);
        assertNotNull(savedBooking, "Сохраненная бронь не должна быть null");
    }

    @Test
    void shouldCreateBookingSuccessfullyAfterCancel() {
        BookingEntity cancelledBooking = new BookingEntity();
        cancelledBooking.setRoom(testRoom);
        cancelledBooking.setBookingInterval(new BookingInterval(LocalDateTime.of(2026, 5, 25, 12, 0),
                LocalDateTime.of(2026, 5, 25, 14, 0)));
        cancelledBooking.setStatus(BookingStatus.CANCELLED);

        Booking newBooking = new Booking(
                null,
                1L,
                1L,
                new BookingInterval(LocalDateTime.of(2026, 5, 25, 11, 0),
                        LocalDateTime.of(2026, 5, 25, 15, 0)),
                null,
                "topic"
        );
        when(bookingRepository.findAllByRoomAndStatus(testRoom, BookingStatus.CONFIRMED))
                .thenReturn(List.of());
        Booking savedBooking = bookingService.createBooking(newBooking);
        assertNotNull(savedBooking, "Сохраненная бронь не должна быть null");
    }
}
