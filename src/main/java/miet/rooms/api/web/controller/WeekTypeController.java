package miet.rooms.api.web.controller;

import miet.rooms.repository.jdbc.model.CycleEventModel;
import miet.rooms.api.service.CycleEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/week-type")
public class WeekTypeController {

    private final CycleEventService cycleEventService;

    @Autowired
    public WeekTypeController(CycleEventService cycleEventService) {
        this.cycleEventService = cycleEventService;
    }

    @GetMapping("by-id")
    public CycleEventModel getWeekTypeById(@RequestParam Long weekTypeId) {
        return cycleEventService.getWeekTypeById(weekTypeId);
    }

    @GetMapping("all")
    public Map<Long, String> getAll() {
        return cycleEventService.findAll();
    }
}
