package miet.rooms.api.web.controller;

import miet.rooms.api.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/config")
public class ConfigController {

    private final WeekDayService weekDayService;
    private final PairService pairService;
    private final RoomTypeService roomTypeService;
    private final SchemeService schemeService;
    private final GroupService groupService;
    private final RoomService roomService;
    private final PeriodicityService periodicityService;
    private final EventTypeService eventTypeService;

    @Autowired
    public ConfigController(RoomService roomService, WeekDayService weekDayService, PairService pairService, RoomTypeService roomTypeService, SchemeService schemeService, GroupService groupService, PeriodicityService periodicityService, EventTypeService eventTypeService) {
        this.roomService = roomService;
        this.weekDayService = weekDayService;
        this.pairService = pairService;
        this.roomTypeService = roomTypeService;
        this.schemeService = schemeService;
        this.groupService = groupService;
        this.periodicityService = periodicityService;
        this.eventTypeService = eventTypeService;
    }

    @GetMapping
    public Map<String, Map<Long, Object>> getMapping() {
        Map<String, Map<Long, Object>> mappings = new HashMap<>();
        mappings.put("weekDays", weekDayService.findAll());
        mappings.put("pairs", pairService.findAll());
        mappings.put("roomTypes", roomTypeService.findAll());
        mappings.put("schemes", schemeService.findAll());
        mappings.put("groups", groupService.findAll());
        mappings.put("rooms", roomService.findAllMapping());
        mappings.put("periodicities", periodicityService.findAll());
        mappings.put("eventTypes", eventTypeService.findAll());
        return mappings;
    }
}
