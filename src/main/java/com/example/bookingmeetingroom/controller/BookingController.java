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
    public ResponseEntity<List<Booking>> getAllBooking(){
        return ResponseEntity.ok(bookingService.getAllBooking());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long id){
        return ResponseEntity.ok(bookingService.getBookingById(id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelRoomById(@PathVariable Long id){
        bookingService.cancelBookingById(id);
        return ResponseEntity.ok().build();
    }

    //FIXME Room? ))
    @PostMapping()
    public ResponseEntity<Booking> createRoom(@RequestBody Booking booking){
        return  ResponseEntity.status(HttpStatus.CREATED).body(bookingService.createBooking(booking));
    }
    @PutMapping("/{id}")
    public ResponseEntity<Booking> updateRoomById(@PathVariable Long id, @RequestBody Booking booking){
        return ResponseEntity.ok(bookingService.updateBookingById(id, booking));
    }
}
