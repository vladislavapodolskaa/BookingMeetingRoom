package com.example.bookingmeetingroom.controller;

import com.example.bookingmeetingroom.domain.BookingAudit;
import com.example.bookingmeetingroom.service.BookingAuditService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/audit")
public class BookingAuditController {
    private final BookingAuditService bookingAuditService;

    public BookingAuditController(BookingAuditService bookingAuditService) {
        this.bookingAuditService = bookingAuditService;
    }

    @GetMapping()
    public ResponseEntity<List<BookingAudit>> getAllBookingAudits(){
        return ResponseEntity.ok(bookingAuditService.findAllBookingAudits());
    }
}
