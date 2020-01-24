package miet.rooms.api.web.controller;

import miet.rooms.api.service.EventService;
import miet.rooms.repository.jpa.entity.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/event")
public class EventController {

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("by-engage-type")
    public List<Event> getAllByEngageType(@RequestParam Long engageTypeId) {
        return eventService.getAllByEngageTypeId(engageTypeId);
    }
}
