package miet.rooms.api.web.controller;

import miet.rooms.api.model.CycleEventModel;
import miet.rooms.api.service.CycleEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/week-type")
//@ComponentScan(basePackages = {"miet.rooms.repository.dao"})
public class WeekTypeController {

    @Autowired
    private CycleEventService cycleEventService;

    @GetMapping("by-id")
    public CycleEventModel getWeekTypeById(@RequestParam Long weekTypeId) {
        return cycleEventService.getWeekTypeById(weekTypeId);
    }

    @GetMapping("all")
    public Map<Long, String> getAll() {
        return cycleEventService.findAll();
    }
}
