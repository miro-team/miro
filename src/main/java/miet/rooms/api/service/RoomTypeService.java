package miet.rooms.api.service;

import miet.rooms.api.web.response.ConfigResponse;
import miet.rooms.repository.jpa.dao.RoomTypeDao;
import miet.rooms.repository.jpa.entity.RoomType;
import org.json.JSONException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomTypeService {

    private final RoomTypeDao roomTypeDao;

    public RoomTypeService(RoomTypeDao roomTypeDao) {
        this.roomTypeDao = roomTypeDao;
    }

    public List<Object> findAllMapping() throws JSONException {
        return roomTypeDao.findAll().stream()
                .map(r -> {
                    ConfigResponse response = new ConfigResponse();
                    response.setId(r.getId());
                    response.setName(r.getName());
                    return response;
                })
                .collect(Collectors.toList());
    }

    public RoomType findAllById(Long id) {
        return roomTypeDao.findAllById(id);
    }
}
