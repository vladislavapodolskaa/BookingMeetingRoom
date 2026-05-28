package com.example.bookingmeetingroom.annotation;

import com.example.bookingmeetingroom.domain.AuditAction;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuditAnnotation {
    AuditAction value();
}
