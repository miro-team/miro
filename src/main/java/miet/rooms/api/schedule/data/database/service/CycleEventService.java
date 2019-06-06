package miet.rooms.api.schedule.data.database.service;

import miet.rooms.api.schedule.data.database.dao.CycleEventDao;
import miet.rooms.api.schedule.data.database.entity.CycleEvent;
import miet.rooms.api.schedule.data.database.model.CycleEventModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CycleEventService {

    @Autowired
    private CycleEventDao cycleEventDao;

    public List<CycleEventModel> getAll() {
        List<CycleEvent> cycleEvents = cycleEventDao.findAll();
        List<CycleEventModel> cycleEventModels = new ArrayList<>();
        for(CycleEvent cycleEvent : cycleEvents) {
            CycleEventModel cycleEventModel = new CycleEventModel();
            cycleEventModel.setId(cycleEvent.getId());
            cycleEventModel.setWeekType(cycleEvent.getWeekType());
            cycleEventModels.add(cycleEventModel);
        }
        return cycleEventModels;
    }

    public CycleEventModel getWeekTypeById(Long id) {
        CycleEvent cycleEvent = cycleEventDao.findAllById(id);
        CycleEventModel cycleEventModel = new CycleEventModel();
        cycleEventModel.setId(cycleEvent.getId());
        cycleEventModel.setWeekType(cycleEvent.getWeekType());
        return cycleEventModel;
    }
}
