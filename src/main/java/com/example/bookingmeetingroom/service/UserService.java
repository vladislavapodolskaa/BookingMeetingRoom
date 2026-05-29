package com.example.bookingmeetingroom.service;

import com.example.bookingmeetingroom.domain.User;
import com.example.bookingmeetingroom.domain.UserResponse;
import com.example.bookingmeetingroom.entity.UserEntity;
import com.example.bookingmeetingroom.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

import static com.example.bookingmeetingroom.domain.UserRole.CLIENT;
import static com.example.bookingmeetingroom.domain.UserStatus.ACTIVE;
import static com.example.bookingmeetingroom.domain.UserStatus.DELETED;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(this::toUserResponse).toList();
    }

    public UserResponse getUserById(Long id) {
        return toUserResponse(userRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found by id = " + id)));
    }

    public void deleteUserById(Long id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("User not found by id = " + id));
        userEntity.setStatus(DELETED);
        userRepository.save(userEntity);
        logger.info("User with id = {} successfully deleted", id);
    }

    public UserResponse createUser(User user) {
        if (user.id() != null) {
            throw new IllegalArgumentException("Id should be null");
        }
        if (userRepository.existsByLogin(user.login())) {
            throw new IllegalArgumentException("Login already exist");
        }

        validatePassword(user.password());
        String passwordHash = passwordEncoder.encode(user.password());

        validateUser(user);

        UserEntity userEntity = new UserEntity(
                null,
                user.name(),
                user.email(),
                user.department(),
                user.login(),
                passwordHash,
                CLIENT,
                ACTIVE
        );
        userRepository.save(userEntity);
        logger.info("User successfully created");
        return toUserResponse(userEntity);
    }

    public UserResponse updateUserById(User user) {
        if (user.id() == null) {
            throw new IllegalArgumentException("Id can't be null");
        }
        UserEntity userEntity = userRepository.findById(user.id()).orElseThrow(() -> new NoSuchElementException("User not found by id = " + user.id()));

        String login = userEntity.getLogin();
        String password = userEntity.getPassword();

        validateUser(user);

        userEntity = new UserEntity(
                user.id(),
                user.name(),
                user.email(),
                user.department(),
                login,
                password,
                CLIENT,
                ACTIVE
        );
        userRepository.save(userEntity);
        logger.info("User with id = {} successfully updated", user.id());
        return toUserResponse(userEntity);
    }

    private UserResponse toUserResponse(UserEntity userEntity) {
        return new UserResponse(
                userEntity.getId(),
                userEntity.getName(),
                userEntity.getEmail(),
                userEntity.getDepartment(),
                userEntity.getLogin());
    }

    private void validateUser(User user) {
        if (user.department() == null) {
            throw new IllegalArgumentException("Department can't be null");
        }
        if (user.name() == null) {
            throw new IllegalArgumentException("Name can't be null");
        }
        if (user.email() == null) {
            throw new IllegalArgumentException("Email can't be null");
        }
    }

    public void validatePassword(String password) {
        if (password == null || password.length() < 8 || password.length() > 32) {
            throw new IllegalArgumentException("Password must be between 8 and 32 characters long");
        }

        if (!password.matches(".*\\d.*")) {
            throw new IllegalArgumentException("Password must contain at least one digit");
        }

        if (!password.matches(".*[A-Z].*")) {
            throw new IllegalArgumentException("Password must contain at least one uppercase letter");
        }

        if (!password.matches(".*[a-z].*")) {
            throw new IllegalArgumentException("Password must contain at least one lowercase letter");
        }

        if (!password.matches(".*[!@#$%^&*()_\\-+=:;.,?].*")) {
            throw new IllegalArgumentException("Password must contain at least one special character");
        }
    }
}
