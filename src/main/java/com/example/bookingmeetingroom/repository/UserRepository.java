package com.example.bookingmeetingroom.repository;

import com.example.bookingmeetingroom.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query("select count(u) = 0 from UserEntity u where u.id = :id")
    boolean notExistsById(@Param("id") Long id);

}
