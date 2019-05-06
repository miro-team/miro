package miet.rooms.api.schedule.controller;

import miet.rooms.api.schedule.data.database.dao.*;
import miet.rooms.api.schedule.data.database.entity.*;
import miet.rooms.api.schedule.data.frontdata.Event;
import miet.rooms.api.util.DateTimeHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/add-event")
public class EventController {

    @Autowired
    private RoomDao roomDao;

    @Autowired
    private AllDataDao allDataDao;

    @Autowired
    private PairDao pairDao;

    @Autowired
    private GroupDao groupDao;

    @Autowired
    private EngageTypeDao engageTypeDao;

    @Autowired
    private CycleEventDao cycleEventDao;

    @PostMapping(value = "/simple")
    public @ResponseBody
    AllData addSimpleEvent(@RequestBody Event event) {
        AllData allData = new AllData();
        if (checkRoomFree(event)) {
            LocalDate date = DateTimeHelper.asLocalDate(event.getDate());
            allData = fillData(event, date);

            allDataDao.save(allData);
        }
        return allData;
    }

    @PostMapping(value = "/cycle")
    public @ResponseBody
    List<AllData> addCycleEvent(@RequestBody Event event) {
        List<AllData> datas = new ArrayList<>();
        int cycleLength = cycleEventDao.findAll().size();
        //TODO: improve cycle. We don't always need 5 cycles
        for(int i = 0; i < 5; i++) {
            if (checkRoomFree(event)) {
                LocalDate date = DateTimeHelper.asLocalDate(event.getDate()).plusWeeks(i * cycleLength);
                AllData allData = fillData(event, date);

                datas.add(allData);
                allDataDao.save(allData);
            }
        }
        return datas;
    }

    private AllData fillData(@RequestBody Event event, LocalDate date) {
        AllData allData = new AllData();
        allData.setDate(date);

        Pair pair = pairDao.findAllById(event.getPairId());
        allData.setPair(pair);

        Room room = roomDao.findAllById(event.getRoomId());
        allData.setRoom(room);

        Group group = groupDao.findAllByName(event.getEngagedBy());
        if (group == null) {
            group = new Group();
            group.setName(event.getEngagedBy());
            groupDao.save(group);
        }
        allData.setGroup(group);

        allData.setWeekType(String.valueOf(event.getWeekType()));

        EngageType engageType = engageTypeDao.findAllByEngageTypeId(event.getEngageTypeId());
        allData.setEngageType(engageType);

        return allData;
    }


    private boolean checkRoomFree(Event event) {
        LocalDate date = DateTimeHelper.asLocalDate(event.getDate());
        List<Group> groups = allDataDao.findGroupByDateAndAndPair_IdAndRoom_Id(date, event.getPairId(), event.getRoomId());
        return groups.isEmpty();
    }
}
