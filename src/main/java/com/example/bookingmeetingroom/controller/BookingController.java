package com.example.bookingmeetingroom.controller;


import com.example.bookingmeetingroom.domain.Booking;
import com.example.bookingmeetingroom.domain.BookingAudit;
import com.example.bookingmeetingroom.service.BookingAuditService;
import com.example.bookingmeetingroom.service.BookingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/booking")
public class BookingController {
    private final BookingService bookingService;
    private final Logger logger = LoggerFactory.getLogger(BookingController.class);
    private final BookingAuditService bookingAuditService;

    public BookingController(BookingService bookingService, BookingAuditService bookingAuditService) {
        this.bookingService = bookingService;
        this.bookingAuditService = bookingAuditService;
    }

    @GetMapping()
    public ResponseEntity<List<Booking>> getAllActualBookings() {
        logger.info("Request to get all actual Booking");
        return ResponseEntity.ok(bookingService.getAllActualBookings());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long id) {
        logger.info("Request to get Booking: {}", id);
        return ResponseEntity.ok(bookingService.getBookingById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelBookingById(@PathVariable Long id) {
        logger.info("Request to cancel Booking: {}", id);
        bookingService.cancelBookingById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping()
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) {
        logger.info("Request to create Booking. User: {}, Room: {}", booking.userId(), booking.roomId());
        return ResponseEntity.status(HttpStatus.CREATED).body(bookingService.createBooking(booking));
    }

    @PutMapping()
    public ResponseEntity<Booking> updateBookingById(@RequestBody Booking booking) {
        logger.info("Request to update Booking. User: {}, Room: {}", booking.userId(), booking.roomId());
        return ResponseEntity.ok(bookingService.updateBookingById(booking));
    }

    @GetMapping("/{id}/audit")
    public ResponseEntity<List<BookingAudit>> getBookingAuditById(@PathVariable Long id) {
        return ResponseEntity.ok(bookingAuditService.getBookingAuditsByBookingId(id));
    }

}
