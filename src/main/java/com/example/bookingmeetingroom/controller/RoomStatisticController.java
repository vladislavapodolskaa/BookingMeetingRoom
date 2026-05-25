package com.example.bookingmeetingroom.controller;

import com.example.bookingmeetingroom.domain.RoomWeeklyStatistic;
import com.example.bookingmeetingroom.service.RoomStatisticService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomStatisticController {
    private final RoomStatisticService roomStatisticService;

    public RoomStatisticController(RoomStatisticService roomStatisticService) {
        this.roomStatisticService = roomStatisticService;
    }

    @GetMapping("/statistic")
    public ResponseEntity<List<RoomWeeklyStatistic>> getRoomsStatistics() {
        return ResponseEntity.ok(roomStatisticService.getAllRoomsStatistics());
    }

    @GetMapping("/{id}/statistic")
    public ResponseEntity<RoomWeeklyStatistic> getRoomWeeklyStatistic(@PathVariable Long id) {
        return ResponseEntity.ok(roomStatisticService.getRoomWeeklyStatistic(id));
    }
}
