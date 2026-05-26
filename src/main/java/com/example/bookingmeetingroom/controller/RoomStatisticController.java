package com.example.bookingmeetingroom.controller;

import com.example.bookingmeetingroom.domain.RoomStatistic;
import com.example.bookingmeetingroom.service.RoomStatisticService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomStatisticController {
    private final RoomStatisticService roomStatisticService;

    public RoomStatisticController(RoomStatisticService roomStatisticService) {
        this.roomStatisticService = roomStatisticService;
    }

    @GetMapping("/statistic")
    public ResponseEntity<List<RoomStatistic>> getRoomsStatistics(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date
    ) {
        LocalDateTime finalDate = (date != null) ? date : LocalDateTime.now().minusWeeks(1);
        return ResponseEntity.ok(roomStatisticService.getAllRoomsStatisticsFromDate(finalDate));
    }

    @GetMapping("/{id}/statistic")
    public ResponseEntity<RoomStatistic> getRoomStatisticFromDate(
            @PathVariable Long id,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date
    ) {
        LocalDateTime finalDate = (date != null) ? date : LocalDateTime.now().minusWeeks(1);
        return ResponseEntity.ok(roomStatisticService.getRoomStatisticFromDate(id, finalDate));
    }
}
