package miet.rooms.api.web.controller;

import miet.rooms.api.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/config")
public class ConfigController {

    private final WeekDayService weekDayService;
    private final PairService pairService;
    private final RoomTypeService roomTypeService;
    private final SchemeService schemeService;
    private final GroupService groupService;
    private final RoomService roomService;
    private final PeriodicityService periodicityService;
    private final EngageTypeService engageTypeService;
    private final TeacherService teacherService;
    private final DisciplineService disciplineService;

    @Autowired
    public ConfigController(RoomService roomService, WeekDayService weekDayService, PairService pairService,
                            RoomTypeService roomTypeService, SchemeService schemeService, GroupService groupService,
                            PeriodicityService periodicityService, EngageTypeService engageTypeService,
                            TeacherService teacherService, DisciplineService disciplineService) {
        this.roomService = roomService;
        this.weekDayService = weekDayService;
        this.pairService = pairService;
        this.roomTypeService = roomTypeService;
        this.schemeService = schemeService;
        this.groupService = groupService;
        this.periodicityService = periodicityService;
        this.engageTypeService = engageTypeService;
        this.teacherService = teacherService;
        this.disciplineService = disciplineService;
    }

    @GetMapping
    public Map<String, List<Object>> getMapping() {
        Map<String, List<Object>> mappings = new HashMap<>();
        mappings.put("weekDays", weekDayService.findAllMapping());
        mappings.put("pairs", pairService.findAllMapping());
        mappings.put("roomTypes", roomTypeService.findAllMapping());
        mappings.put("schemes", schemeService.findAllMapping());
        mappings.put("groups", groupService.findAllMapping());
        mappings.put("rooms", roomService.findAllMapping());
        mappings.put("periodicities", periodicityService.findAllMapping());
        mappings.put("eventTypes", engageTypeService.findAllMapping());
        mappings.put("disciplines", disciplineService.findAllMapping());
        mappings.put("teachers", teacherService.findAllMapping());
        return mappings;
    }
}
