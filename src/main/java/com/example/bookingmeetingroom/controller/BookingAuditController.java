package com.example.bookingmeetingroom.controller;

import com.example.bookingmeetingroom.domain.Audit;
import com.example.bookingmeetingroom.service.AuditService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/audit")
public class BookingAuditController {
    private final AuditService auditService;

    public BookingAuditController(AuditService auditService) {
        this.auditService = auditService;
    }

    @GetMapping()
    public ResponseEntity<List<Audit>> getAllBookingAudits() {
        return ResponseEntity.ok(auditService.findAllBookingAudits());
    }


    @GetMapping("room/{id}")
    public ResponseEntity<List<Audit>> getBookingAuditsByRoomId(@PathVariable Long id) {
        return ResponseEntity.ok(auditService.getBookingAuditsByRoomId(id));
    }

    @GetMapping("user/{id}")
    public ResponseEntity<List<Audit>> getBookingAuditsByUserId(@PathVariable Long id) {
        return ResponseEntity.ok(auditService.getBookingAuditsByUserId(id));
    }

    @GetMapping("booking/{id}")
    public ResponseEntity<List<Audit>> getBookingAuditById(@PathVariable Long id) {
        return ResponseEntity.ok(auditService.getAuditsByBookingId(id));
    }
}
