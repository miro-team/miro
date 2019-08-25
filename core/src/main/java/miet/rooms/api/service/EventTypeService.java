package miet.rooms.api.service;

import miet.rooms.repository.jpa.dao.EventTypeDao;
import miet.rooms.repository.jpa.entity.EventType;
import org.json.JSONException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EventTypeService {
    private final EventTypeDao eventTypeDao;

    public EventTypeService(EventTypeDao eventTypeDao) {
        this.eventTypeDao = eventTypeDao;
    }

    public Map<Long, Object> findAll() throws JSONException {
        Map<Long, Object> eventConfig = new HashMap<>();
        List<EventType> eventTypes = eventTypeDao.findAll();
        for (EventType eventType : eventTypes) {
            eventConfig.put(eventType.getId(), eventType.getName());
        }
        return eventConfig;
    }

}
