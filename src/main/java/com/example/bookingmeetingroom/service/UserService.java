package com.example.bookingmeetingroom.service;

import com.example.bookingmeetingroom.domain.User;
import com.example.bookingmeetingroom.entity.UserEntity;
import com.example.bookingmeetingroom.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll().stream().map(this::toUser).toList();
    }

    public User getUserById(Long id) {
        return toUser(userRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found by id = " + id)));
    }

    public void deleteUserById(Long id) {
        if (userRepository.notExistsById(id)) {
            throw new NoSuchElementException("User not found by id = " + id);
        }
        userRepository.deleteById(id);
    }

    public User createUser(User user) {
        if (user.id() != null) {
            throw new IllegalArgumentException("Id should be null");
        }

        validateUser(user);

        UserEntity userEntity = new UserEntity(
                null,
                user.name(),
                user.email(),
                user.department()
        );
        userRepository.save(userEntity);
        return toUser(userEntity);
    }

    public User updateUserById(User user) {
        if (user.id() == null) {
            throw new IllegalArgumentException("Id can't be null");
        }
        if (userRepository.notExistsById(user.id())) {
            throw new NoSuchElementException("User not found by id = " + user.id());
        }

        validateUser(user);

        UserEntity userEntity = new UserEntity(
                user.id(),
                user.name(),
                user.email(),
                user.department()
        );
        userRepository.save(userEntity);
        return toUser(userEntity);
    }

    private User toUser(UserEntity userEntity) {
        return new User(userEntity.getId(),
                userEntity.getName(),
                userEntity.getEmail(),
                userEntity.getDepartment());
    }

    private void validateUser(User user) {
        if (user.department() == null) {
            throw new NullPointerException("Department can't be null");
        }
        if (user.name() == null) {
            throw new NullPointerException("Name can't be null");
        }
        if (user.email() == null) {
            throw new NullPointerException("Email can't be null");
        }
    }
}
