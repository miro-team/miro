package miet.rooms.api.service;

import miet.rooms.api.model.CycleEventModel;
import miet.rooms.repository.dao.CycleEventDao;
import miet.rooms.repository.entity.CycleEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
//@ComponentScan(basePackages = {"miet.rooms.repository.dao.*"})
public class CycleEventService {

    @Autowired
    private CycleEventDao cycleEventDao;

    public Map<Long, String> findAll() {
        List<CycleEvent> cycleEventList = cycleEventDao.findAll();
        Map<Long, String> cycleEvents = new HashMap<>();
        for(CycleEvent cycleEvent : cycleEventList) {
            cycleEvents.put(cycleEvent.getId(), cycleEvent.getWeekType());
        }
        return cycleEvents;
    }

    public CycleEventModel getWeekTypeById(Long id) {
        CycleEvent cycleEvent = cycleEventDao.findAllById(id);
        CycleEventModel cycleEventModel = new CycleEventModel();
        cycleEventModel.setId(cycleEvent.getId());
        cycleEventModel.setWeekType(cycleEvent.getWeekType());
        return cycleEventModel;
    }
}
