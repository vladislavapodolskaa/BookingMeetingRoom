package com.example.bookingmeetingroom.controller;

import com.example.bookingmeetingroom.domain.AuditAction;
import com.example.bookingmeetingroom.domain.BookingEvent;
import com.example.bookingmeetingroom.service.AuditService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class AuditListener {
    private final AuditService auditService;

    public AuditListener(AuditService auditService) {
        this.auditService = auditService;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void auditHandler(BookingEvent event) {
        AuditAction auditAction = event.action();
        Long entityId = event.entityId();
        auditService.auditBooking(auditAction, entityId);
    }
}
