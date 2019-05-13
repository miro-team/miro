package miet.rooms.api.schedule.data.database.controller;

import miet.rooms.api.schedule.data.database.dao.RoomDao;
import miet.rooms.api.schedule.data.database.entity.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/room")
public class RoomController {

    @Autowired
    private RoomDao roomDao;

    @GetMapping
    public Room getRoomByName(@RequestParam String roomName) {
        return roomDao.findAllByName(roomName);
    }
}
