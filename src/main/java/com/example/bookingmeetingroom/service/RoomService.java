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

    public List<Room> getAllRoom(){
        return roomRepository.findAll().stream().map(this::toRoom).toList();
    }
    private Room toRoom(RoomEntity roomEntity){
        return new Room(roomEntity.getId(),
                roomEntity.getName(),
                roomEntity.getCapacity());
    }

    public Room getRoomById(Long id) {
        return roomRepository.findById(id).map(this::toRoom)
                        .orElseThrow(() -> new NoSuchElementException("Room not exist by id = " + id));
    }

    public void deleteRoomById(Long id) {
        if (!roomRepository.existsById(id)){
            throw new NoSuchElementException("Room not exist by id = " + id);
        }
        roomRepository.deleteById(id);
        logger.info("Room with id = {} successfully deleted", id);
    }

    public Room createRoom(Room room) {
        if (room.id() != null){
            throw new IllegalArgumentException("Id should be null");
        }
        RoomEntity roomEntity = new RoomEntity(
                null,
                room.name(),
                room.capacity());
        roomRepository.save(roomEntity);
        logger.info("Room successfully created");
        return toRoom(roomEntity);
    }
    public Room updateRoomById(Long id, Room room){
        if (!roomRepository.existsById(id)){
            throw new NoSuchElementException("Room not exist by id = " + id);
        }
        if (room.id() != null){
            throw new IllegalArgumentException("Id should be null");
        }

        RoomEntity roomEntity = new RoomEntity(
                id,
                room.name(),
                room.capacity());
        roomRepository.save(roomEntity);
        logger.info("Room with id = {} successfully updated", id);
        return toRoom(roomEntity);
    }
}
