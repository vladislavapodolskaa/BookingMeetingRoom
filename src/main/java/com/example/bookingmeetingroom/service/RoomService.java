package com.example.bookingmeetingroom.service;

import com.example.bookingmeetingroom.domain.Room;
import com.example.bookingmeetingroom.entity.RoomEntity;
import com.example.bookingmeetingroom.repository.RoomRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class RoomService {
    private final RoomRepository roomRepository;
    private final Logger logger = LoggerFactory.getLogger(RoomService.class);

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<Room> getAllRooms() {
        return roomRepository.findAll().stream().map(this::toRoom).toList();
    }

    public Room getRoomById(Long id) {
        return roomRepository.findById(id).map(this::toRoom)
                .orElseThrow(() -> new NoSuchElementException("Room not exist by id = " + id));
    }

    public void deleteRoomById(Long id) {
        if (roomRepository.notExistsById(id)) {
            throw new NoSuchElementException("Room not exist by id = " + id);
        }
        roomRepository.deleteById(id);
        logger.info("Room with id = {} successfully deleted", id);
    }

    public Room createRoom(Room room) {
        if (room.id() != null) {
            throw new IllegalArgumentException("Id should be null");
        }
        validateRoom(room);

        RoomEntity roomEntity = new RoomEntity(
                null,
                room.name(),
                room.capacity());
        roomRepository.save(roomEntity);
        logger.info("Room successfully created");
        return toRoom(roomEntity);
    }

    public Room updateRoomById(Room room) {
        if (room.id() == null) {
            throw new IllegalArgumentException("Id can't be null");
        }

        if (roomRepository.notExistsById(room.id())) {
            throw new NoSuchElementException("Room not exist by id = " + room.id());
        }

        validateRoom(room);

        RoomEntity roomEntity = new RoomEntity(
                room.id(),
                room.name(),
                room.capacity());
        roomRepository.save(roomEntity);
        logger.info("Room with id = {} successfully updated", room.id());
        return toRoom(roomEntity);
    }

    private Room toRoom(RoomEntity roomEntity) {
        return new Room(roomEntity.getId(),
                roomEntity.getName(),
                roomEntity.getCapacity());
    }

    private void validateRoom(Room room) {
        if (room.name() == null) {
            throw new IllegalArgumentException("Name can't ne null");
        }
        if (room.capacity() <= 0) {
            throw new IllegalArgumentException("Room's capacity should be positive");
        }
    }
}
