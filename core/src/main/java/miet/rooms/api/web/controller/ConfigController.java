package miet.rooms.api.web.controller;

import miet.rooms.api.service.CycleEventService;
import miet.rooms.api.service.RoomService;
import miet.rooms.api.service.WeekDayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/mapping")
public class ConfigController {

    private RoomService roomService;
    private CycleEventService cycleEventService;
    private WeekDayService weekDayService;

    @Autowired
    public ConfigController(RoomService roomService, CycleEventService cycleEventService, WeekDayService weekDayService) {
        this.roomService = roomService;
        this.cycleEventService = cycleEventService;
        this.weekDayService = weekDayService;
    }

    @GetMapping
    public Map<String, Map<Long, String>> getMapping() {
        Map<String, Map<Long, String>> mappings = new HashMap<>();
        mappings.put("rooms", roomService.findAll());
        mappings.put("weekTypes", cycleEventService.findAll());
        mappings.put("weekDays", weekDayService.findAll());
        return mappings;
    }
}
