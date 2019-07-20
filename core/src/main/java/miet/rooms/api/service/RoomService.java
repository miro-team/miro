package miet.rooms.api.service;

import miet.rooms.api.model.RoomModel;
import miet.rooms.repository.dao.RoomDao;
import miet.rooms.repository.entity.Room;
import org.json.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
//@ComponentScan(basePackages = {"miet.rooms.repository.dao.*"})
public class RoomService {

    @Autowired
    private RoomDao roomDao;

    public Map<Long, String> findAll() throws JSONException {
        Map<Long, String> rooms = new HashMap<>();
        List<Room> roomList = roomDao.findAll();
        for(Room room : roomList) {
            rooms.put(room.getId(), room.getName());
        }
        return rooms;
    }

    public RoomModel findAllById(Long id) {
        Room room = roomDao.findAllById(id);
        RoomModel roomModel = new RoomModel();
        roomModel.setId(room.getId());
        roomModel.setName(room.getName());
        return roomModel;
    }
}
