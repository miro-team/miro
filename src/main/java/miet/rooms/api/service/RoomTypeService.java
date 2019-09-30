package miet.rooms.api.service;

import miet.rooms.repository.jpa.dao.RoomTypeDao;
import miet.rooms.repository.jpa.entity.RoomType;
import org.json.JSONException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoomTypeService {

    private final RoomTypeDao roomTypeDao;

    public RoomTypeService(RoomTypeDao roomTypeDao) {
        this.roomTypeDao = roomTypeDao;
    }

    public Map<Long, Object> findAll() throws JSONException {
        Map<Long, Object> roomTypeConfig = new HashMap<>();
        List<RoomType> roomTypes = roomTypeDao.findAll();
        for (RoomType roomType : roomTypes) {
            roomTypeConfig.put(roomType.getId(), roomType.getName());
        }
        return roomTypeConfig;
    }

    public RoomType findAllById(Long id) {
        return roomTypeDao.findAllById(id);
    }
}
