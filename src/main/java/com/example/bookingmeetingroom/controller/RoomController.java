package com.example.bookingmeetingroom.controller;

import com.example.bookingmeetingroom.domain.Room;
import com.example.bookingmeetingroom.service.RoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomController {
    private final RoomService roomService;
    private final Logger logger = LoggerFactory.getLogger(RoomController.class);

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }
    @GetMapping()
    public ResponseEntity<List<Room>> getAllRoom(){
        List<Room> rooms = roomService.getAllRoom();
        return ResponseEntity.ok(rooms);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Room> getRoomById(@PathVariable Long id){
        return ResponseEntity.ok(roomService.getRoomById(id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoomById(@PathVariable Long id){
        logger.info("Request to delete Room with id = {}", id);
        roomService.deleteRoomById(id);
        return ResponseEntity.ok().build();
    }
    @PostMapping()
    public ResponseEntity<Room> createRoom(@RequestBody Room room){
        logger.info("Request to create Room");
        return  ResponseEntity.status(HttpStatus.CREATED).body(roomService.createRoom(room));
    }
    @PutMapping("/{id}")
    public ResponseEntity<Room> updateRoomById(@PathVariable Long id, @RequestBody Room room){
        logger.info("Request to update Room with id = {}", id);
        return ResponseEntity.ok(roomService.updateRoomById(id, room));
    }
}
