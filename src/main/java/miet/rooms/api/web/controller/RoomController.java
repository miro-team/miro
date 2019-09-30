package miet.rooms.api.web.controller;

import miet.rooms.api.service.RoomService;
import miet.rooms.repository.jdbc.model.RoomModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/room")
public class RoomController {

    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/by-id")
    public RoomModel getRoomByName(@RequestParam Long roomId) {
        return roomService.findAllById(roomId);
    }
}
