package com.example.bookingmeetingroom.repository;

import com.example.bookingmeetingroom.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
