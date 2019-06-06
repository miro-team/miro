package miet.rooms.api.schedule.data.database.controller;

import miet.rooms.api.schedule.data.database.model.CycleEventModel;
import miet.rooms.api.schedule.data.database.service.CycleEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/week-type")
public class WeekTypeController {

    @Autowired
    private CycleEventService cycleEventService;

    @GetMapping("by-id")
    public CycleEventModel getWeekTypeById(@RequestParam Long id) {
        return cycleEventService.getWeekTypeById(id);
    }

    @GetMapping("all")
    public List<CycleEventModel> getAll() {
        return cycleEventService.getAll();
    }
}
