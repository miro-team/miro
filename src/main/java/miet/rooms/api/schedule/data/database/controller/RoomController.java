package miet.rooms.api.schedule.data.database.controller;

import miet.rooms.api.schedule.data.database.dao.RoomDao;
import miet.rooms.api.schedule.data.database.entity.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/room")
public class RoomController {

    @Autowired
    private RoomDao roomDao;

    @GetMapping("/by-id")
    public Room getRoomByName(@RequestParam Long roomId) {
        return roomDao.findAllById(roomId);
    }

    @GetMapping("all")
    public List<Room> getAllRooms() {
        return roomDao.findAll();
    }
}
