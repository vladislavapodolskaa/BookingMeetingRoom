package com.example.bookingmeetingroom.service;

import com.example.bookingmeetingroom.domain.Audit;
import com.example.bookingmeetingroom.domain.AuditAction;
import com.example.bookingmeetingroom.entity.AuditEntity;
import com.example.bookingmeetingroom.entity.BookingEntity;
import com.example.bookingmeetingroom.entity.UserEntity;
import com.example.bookingmeetingroom.repository.AuditRepository;
import com.example.bookingmeetingroom.repository.BookingRepository;
import com.example.bookingmeetingroom.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import static com.example.bookingmeetingroom.domain.AuditEntityType.BOOKING;

@Service
public class AuditService {
    private final AuditRepository auditRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;

    public AuditService(AuditRepository auditRepository, UserRepository userRepository, BookingRepository bookingRepository) {
        this.auditRepository = auditRepository;
        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
    }

    public List<Audit> getAuditsByBookingId(Long id) {
        return auditRepository.findByEntityTypeAndEntityId(BOOKING, id).stream().map(this::toAudit).toList();
    }

    public List<Audit> getBookingAuditsByRoomId(Long id) {
        return auditRepository.findBookingAuditByRoomId(id).stream().map(this::toAudit).toList();
    }

    public List<Audit> findAllBookingAudits() {
        return auditRepository.findAllByEntityType(BOOKING).stream().map(this::toAudit).toList();
    }

    public List<Audit> getBookingAuditsByUserId(Long id) {
        return auditRepository.findAllByEntityTypeAndUserId(BOOKING, id).stream().map(this::toAudit).toList();
    }


    public void auditBooking(AuditAction action, long bookingId) {
        BookingEntity bookingEntity = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NoSuchElementException("Booking not found by id = " + bookingId));
        UserEntity userEntity = userRepository.findById(bookingEntity.getUser().getId())
                .orElseThrow(() -> new NoSuchElementException("User not found by id = " + bookingEntity.getUser().getId()));

        AuditEntity auditEntity = new AuditEntity(
                null,
                action,
                LocalDateTime.now(),
                userEntity.getId(),
                BOOKING,
                bookingEntity.getId()
        );
        auditRepository.save(auditEntity);
    }

    private Audit toAudit(AuditEntity auditEntity) {
        return new Audit(
                auditEntity.getId(),
                auditEntity.getAction(),
                auditEntity.getTime(),
                auditEntity.getUserId(),
                auditEntity.getEntityType(),
                auditEntity.getEntityId()
        );
    }
}