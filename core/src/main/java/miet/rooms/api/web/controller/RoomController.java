package miet.rooms.api.web.controller;

import miet.rooms.repository.jdbc.model.RoomModel;
import miet.rooms.api.service.RoomService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/room")
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

    @GetMapping("all")
    public Map<Long, String> getAllRooms() throws JSONException {
        return roomService.findAll();
    }
}
