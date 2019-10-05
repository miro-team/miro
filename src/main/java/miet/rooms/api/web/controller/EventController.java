package miet.rooms.api.web.controller;

import miet.rooms.api.service.AllDataService;
import miet.rooms.repository.jpa.entity.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/event")
public class EventController {

    private final AllDataService allDataService;

    @Autowired
    public EventController(AllDataService allDataService) {
        this.allDataService = allDataService;
    }

    @GetMapping("by-engage-type")
    public List<Event> getAllByEngageType(@RequestParam Long engageTypeId) {
        return allDataService.getAllByEngageTypeId(engageTypeId);
    }
}
