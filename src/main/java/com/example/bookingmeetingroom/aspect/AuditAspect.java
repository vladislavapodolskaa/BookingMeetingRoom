package com.example.bookingmeetingroom.aspect;

import com.example.bookingmeetingroom.annotation.AuditAnnotation;
import com.example.bookingmeetingroom.domain.AuditAction;
import com.example.bookingmeetingroom.domain.Booking;
import com.example.bookingmeetingroom.service.AuditService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AuditAspect {
    private final AuditService auditService;

    public AuditAspect(AuditService auditService) {
        this.auditService = auditService;
    }

    @Pointcut("@annotation(auditAnnotation)")
    public void auditPointcut(AuditAnnotation auditAnnotation) {
    }

    @AfterReturning(pointcut = "auditPointcut(auditAnnotation)", returning = "result", argNames = "joinPoint,auditAnnotation,result")
    public void afterReturningAuditAnnotation(JoinPoint joinPoint, AuditAnnotation auditAnnotation, Object result) {
        AuditAction auditAction = auditAnnotation.value();
        Long entityId;
        if (result != null) {
            if (result instanceof Booking booking) {
                entityId = booking.id();
            } else {
                throw new IllegalArgumentException("Unexpected result type for auditing: " + result.getClass().getName());
            }
        } else {
            Object[] args = joinPoint.getArgs();
            if (args.length == 1 && args[0] instanceof Long id) {
                entityId = id;
            } else {
                throw new IllegalArgumentException("Auditing failed: Expected Long as the single argument for a void method");
            }
        }
        auditService.auditBooking(auditAction, entityId);
    }
}
