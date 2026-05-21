package com.example.bookingmeetingroom.controller;


import com.example.bookingmeetingroom.domain.Booking;
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

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping()
    public ResponseEntity<List<Booking>> getAllBooking(){
        logger.info("Request to get All Booking");
        return ResponseEntity.ok(bookingService.getAllBooking());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long id){
        logger.info("Request to get Booking: {}", id);
        return ResponseEntity.ok(bookingService.getBookingById(id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelRoomById(@PathVariable Long id){
        logger.info("Request to cancel Booking: {}", id);
        bookingService.cancelBookingById(id);
        return ResponseEntity.ok().build();
    }
    @PostMapping()
    public ResponseEntity<Booking> createRoom(@RequestBody Booking booking){
        logger.info("Request to create Booking. User: {}, Room: {}", booking.userId(), booking.roomId());
        return  ResponseEntity.status(HttpStatus.CREATED).body(bookingService.createBooking(booking));
    }
    @PutMapping("/{id}")
    public ResponseEntity<Booking> updateRoomById(@PathVariable Long id, @RequestBody Booking booking){
        logger.info("Request to update Booking. User: {}, Room: {}", booking.userId(), booking.roomId());
        return ResponseEntity.ok(bookingService.updateBookingById(id, booking));
    }
}
