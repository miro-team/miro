package miet.rooms.api.service;

import miet.rooms.api.web.response.RoomConfigResponse;
import miet.rooms.repository.jdbc.model.RoomModel;
import miet.rooms.repository.jpa.dao.RoomDao;
import miet.rooms.repository.jpa.entity.Room;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService {

    private final RoomDao roomDao;

    @Autowired
    public RoomService(RoomDao roomDao) {
        this.roomDao = roomDao;
    }

    public List<Object> findAllMapping() throws JSONException {
        return roomDao.findAll().stream()
                .map(r -> RoomConfigResponse.builder()
                        .id(r.getId())
                        .capacity(r.getCapacity())
                        .name(r.getName())
                        .schemeId(r.getScheme().getId())
                        .roomTypeId(r.getRoomType().getId())
                        .schemeMapping(r.getSchemeMapping())
                        .build()
                )
                .collect(Collectors.toList());
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
