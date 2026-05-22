package com.example.bookingmeetingroom.controller;

import com.example.bookingmeetingroom.domain.BookingAudit;
import com.example.bookingmeetingroom.domain.User;
import com.example.bookingmeetingroom.service.BookingAuditService;
import com.example.bookingmeetingroom.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final BookingAuditService bookingAuditService;
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService, BookingAuditService bookingAuditService) {
        this.userService = userService;
        this.bookingAuditService = bookingAuditService;
    }

    @GetMapping()
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        logger.info("Request to delete user with id = {}", id);
        userService.deleteUserById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping()
    public ResponseEntity<User> createUser(@RequestBody User user) {
        logger.info("Request to create user");
        return  ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(user));
    }

    @PutMapping()
    public ResponseEntity<User> updateUserById(@RequestBody User user) {
        logger.info("Request to update user with id = {}", user.id());
        return ResponseEntity.ok(userService.updateUserById(user));
    }
    @GetMapping("/{id}/audit")
    public ResponseEntity<List<BookingAudit>> getBookingAuditsByUserId(@PathVariable Long id){
        return ResponseEntity.ok(bookingAuditService.getBookingAuditsByUserId(id));
    }
}
