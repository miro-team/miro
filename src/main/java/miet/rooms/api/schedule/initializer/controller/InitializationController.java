package miet.rooms.api.schedule.initializer.controller;

import miet.rooms.api.schedule.data.database.dao.AllDataDao;
import miet.rooms.api.schedule.data.database.dao.GroupDao;
import miet.rooms.api.schedule.data.database.dao.PairDao;
import miet.rooms.api.schedule.data.database.dao.RoomDao;
import miet.rooms.api.schedule.data.database.entity.*;
import miet.rooms.api.schedule.data.initdata.Datum;
import miet.rooms.api.schedule.data.initdata.TimetableData;
import miet.rooms.api.schedule.initializer.ScheduleGetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/initialize")
public class InitializationController {
    @Autowired
    private ScheduleGetter scheduleGetter;

    @Autowired
    private PairDao pairDao;

    @Autowired
    private AllDataDao allDataDao;

    @Autowired
    private GroupDao groupDao;

    @Autowired
    private RoomDao roomDao;

    @PostMapping
    public @ResponseBody
    List<TimetableData> initialize(
            @RequestParam(value = "weekAmount") Long weekAmount,
            @RequestParam(value = "startDate") @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate startDate
    ) throws IOException {
        List<TimetableData> schedule = getScheduleFromServer();
        List<Datum> datumList = schedule.stream().flatMap(ttd -> ttd.getData().stream()).collect(Collectors.toList());
        long maxCycleWeekNumber = datumList.stream().mapToLong(Datum::getWeekNumber).max().orElse(0);
        initializeGroups();
        initializeRooms();
        //TODO: day of weeks must be checked
        for (int weekNum = 0; weekNum < weekAmount; weekNum += maxCycleWeekNumber) {
            for (int weekType = 0; weekType < maxCycleWeekNumber; weekType++) {
                List<Datum> weekData = getDatumForCertainWeek(datumList, weekType);
                for (Datum datum : weekData) {
                    AllData allData = new AllData();

                    LocalDate realDay = startDate.plusWeeks(weekType).plusWeeks(weekType).plusDays(datum.getDay());
                    allData.setDate(realDay);

                    Pair pair = getPair(datum);
                    allData.setPair(pair);

                    Room room = roomDao.findAllByName(datum.getRoom().getName());
                    allData.setRoom(room);

                    Group group = new Group();
                    group.setName(datum.getGroup().getName());
                    allData.setGroup(group);

                    allData.setWeekType(String.valueOf(weekType)); //TODO:temp. Need table for weeks

                    allDataDao.save(allData);
                }
            }
        }
        return schedule;
    }

    private void initializeRooms() {
        List<Room> rooms = scheduleGetter.getRooms().stream()
                .map(str -> {
                    Room room = new Room();
                    room.setName(str);
                    return room;
                    //TODO: add scheme here by parsing or smth like this, I don't know
                }).collect(Collectors.toList());
        roomDao.saveAll(rooms);
    }

    private void initializeGroups() {
        List<Group> groups = scheduleGetter.getGroups().stream().map(str -> {
            Group group = new Group();
            group.setName(str);
            return group;
        }).collect(Collectors.toList());
        groupDao.saveAll(groups);
    }

    private Pair getPair(Datum datum) {
        return pairDao.findAllByName(datum.getPair().getPairStr().trim());
    }

    private List<TimetableData> getScheduleFromServer() throws IOException {
        return scheduleGetter.getScheduleFromServer();
    }

    private List<Datum> getDatumForCertainWeek(List<Datum> datumList, int weekNumber) {
        return datumList.stream().filter(d -> d.getWeekNumber() == weekNumber).collect(Collectors.toList());
    }
}
