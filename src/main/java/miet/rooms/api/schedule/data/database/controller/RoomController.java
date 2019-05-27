package miet.rooms.api.schedule.data.database.controller;

import miet.rooms.api.schedule.data.database.dao.RoomDao;
import miet.rooms.api.schedule.data.database.entity.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/room")
public class RoomController {

    @Autowired
    private RoomDao roomDao;

    @GetMapping("/by-name")
    public Room getRoomByName(@RequestParam String roomName) {
        return roomDao.findAllByName(roomName);
    }

    @GetMapping("/by-id")
    public Room getRoomByName(@RequestParam Long roomId) {
        return roomDao.findAllById(roomId);
    }
}
