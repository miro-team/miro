package miet.rooms.api.service;

import miet.rooms.repository.jpa.dao.EngageTypeDao;
import miet.rooms.repository.jpa.entity.EngageType;
import org.json.JSONException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EventTypeService {
    private final EngageTypeDao engageTypeDao;

    public EventTypeService(EngageTypeDao engageTypeDao) {
        this.engageTypeDao = engageTypeDao;
    }

    public Map<Long, Object> findAll() throws JSONException {
        Map<Long, Object> eventConfig = new HashMap<>();
        List<EngageType> engageTypes = engageTypeDao.findAll();
        for (EngageType engageType : engageTypes) {
            eventConfig.put(engageType.getId(), engageType.getName());
        }
        return eventConfig;
    }

}
