package com.example.bookingmeetingroom.Service;

import com.example.bookingmeetingroom.Domain.Room;
import com.example.bookingmeetingroom.Entity.RoomEntity;
import com.example.bookingmeetingroom.Repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class RoomService {
    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<Room> getAllRoom(){
        return roomRepository.findAll().stream().map(it -> toRoom(it)).toList();
    }
    private Room toRoom(RoomEntity roomEntity){
        return new Room(roomEntity.getId(),
                roomEntity.getName(),
                roomEntity.getCapacity());
    }

    public Room getRoomById(Long id) {
        return roomRepository.findById(id).map(it -> toRoom(it))
                        .orElseThrow(() -> new NoSuchElementException("Room not exist by id = " + id));
    }

    public void deleteRoomById(Long id) {
        if (!roomRepository.existsById(id)){
            throw new NoSuchElementException("Room not exist by id = " + id);
        }
        roomRepository.deleteById(id);
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
        return toRoom(roomEntity);
    }
}
