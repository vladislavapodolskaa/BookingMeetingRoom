package com.example.bookingmeetingroom.service;

import com.example.bookingmeetingroom.domain.User;
import com.example.bookingmeetingroom.entity.UserEntity;
import com.example.bookingmeetingroom.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll().stream().map(this::toUser).toList();
    }
    private User toUser (UserEntity userEntity){
        return new User(userEntity.getId(),
                userEntity.getName(),
                userEntity.getEmail(),
                userEntity.getDepartment());
    }

    public User getUserById(Long id) {
        return toUser(userRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found by id = " + id)));
    }

    public void deleteUserById(Long id) {
        if (!userRepository.existsById(id)){
            throw new NoSuchElementException("User not found by id = " + id);
        }
        userRepository.deleteById(id);
        logger.info("User with id = {} successfully deleted", id);
    }

    public User createUser(User user) {
        if (user.id() != null){
            throw new IllegalArgumentException("Id should be null");
        }
        UserEntity userEntity = new UserEntity(
                null,
                user.name(),
                user.email(),
                user.department()
        );
        userRepository.save(userEntity);
        logger.info("User successfully created");
        return toUser(userEntity);
    }

    public User updateUserById(Long id, User user) {
        if (!userRepository.existsById(id)){
            throw new NoSuchElementException("User not found by id = " + id);
        }
        if (user.id() != null){
            throw new IllegalArgumentException("Id should be null");
        }
        UserEntity userEntity = new UserEntity(
                id,
                user.name(),
                user.email(),
                user.department()
        );
        userRepository.save(userEntity);
        logger.info("User with id = {} successfully updated", id);
        return toUser(userEntity);
    }
}
