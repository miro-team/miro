package miet.rooms.api.service;

import miet.rooms.repository.jdbc.model.RoomModel;
import miet.rooms.repository.jpa.dao.RoomDao;
import miet.rooms.repository.jpa.entity.Room;
import org.json.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoomService {

    private final RoomDao roomDao;

    @Autowired
    public RoomService(RoomDao roomDao) {
        this.roomDao = roomDao;
    }

    public Map<Long, Object> findAllMapping() throws JSONException {
        Map<Long, Object> rooms = new HashMap<>();
        List<Room> roomList = roomDao.findAll();
        for(Room room : roomList) {
            rooms.put(room.getId(), room.getName());
        }
        return rooms;
    }

    public List<Room> findAll() {
        return roomDao.findAll();
    }

    public RoomModel findAllById(Long id) {
        Room room = roomDao.findAllById(id);
        RoomModel roomModel = new RoomModel();
        roomModel.setId(room.getId());
        roomModel.setName(room.getName());
        return roomModel;
    }

    public void saveAll(List<Room> rooms) {
        roomDao.saveAll(rooms);
    }
}
