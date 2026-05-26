package com.example.bookingmeetingroom.controller;


import com.example.bookingmeetingroom.domain.Booking;
import com.example.bookingmeetingroom.service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/booking")
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping()
    public ResponseEntity<List<Booking>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.getBookingById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelBookingById(@PathVariable Long id) {
        bookingService.cancelBookingById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping()
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookingService.createBooking(booking));
    }

    @PutMapping()
    public ResponseEntity<Booking> updateBookingById(@RequestBody Booking booking) {
        return ResponseEntity.ok(bookingService.updateBookingById(booking));
    }
}
