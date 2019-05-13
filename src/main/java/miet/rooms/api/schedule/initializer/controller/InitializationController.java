package miet.rooms.api.schedule.initializer.controller;

import lombok.extern.slf4j.Slf4j;
import miet.rooms.api.schedule.data.database.dao.*;
import miet.rooms.api.schedule.data.database.entity.*;
import miet.rooms.api.schedule.data.initdata.Datum;
import miet.rooms.api.schedule.data.initdata.TimetableData;
import miet.rooms.api.schedule.initializer.ScheduleGetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/initialize")
@Slf4j
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

    private List<TimetableData> schedule;

    @PostMapping(value = "/all")
    public List<TimetableData> initializeAll(
            @RequestParam(value = "weekAmount") Long weekAmount,
            @RequestParam(value = "startDate") @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate startDate) throws IOException {
        retrieveDataFromServer();
        initializeRooms();
        initializeGroups();
        return initializeSchedule(weekAmount, startDate);
    }

    @PostMapping(value = "/rooms")
    public void initializeRooms() {
        List<Room> rooms = scheduleGetter.getRooms().stream()
                .map(str -> {
                    Room room = new Room();
                    room.setName(str);
                    return room;
                    //TODO: add scheme here by parsing or smth like this, I don't know
                }).collect(Collectors.toList());
        roomDao.saveAll(rooms);
    }

    @PostMapping(value = "/groups")
    public void initializeGroups() {
        List<Group> groups = scheduleGetter.getGroups().stream().map(str -> {
            Group group = new Group();
            group.setName(str);
            return group;
        }).collect(Collectors.toList());
        groupDao.saveAll(groups);
    }

    @PostMapping(value = "/schedule")
    public @ResponseBody
    List<TimetableData> initializeSchedule(
            @RequestParam(value = "weekAmount") Long weekAmount,
            @RequestParam(value = "startDate") @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate startDate
    ) throws IOException {
        List<Datum> datumList = schedule.stream().flatMap(ttd -> ttd.getData().stream()).collect(Collectors.toList());
        int maxCycleWeekNumber = (int) datumList.stream().mapToLong(Datum::getWeekNumber).max().orElse(0) + 1;
        initializeFirstCycle(datumList, startDate, maxCycleWeekNumber);

        for (int weekNum = maxCycleWeekNumber; weekNum < weekAmount; weekNum += maxCycleWeekNumber) {
            for (int weekType = 0; weekType < maxCycleWeekNumber; weekType++) {
                List<Datum> weekData = getDatumForCertainWeek(datumList, weekType);
                for (Datum datum : weekData) {
                    int daysShift = 7 - startDate.getDayOfWeek().getValue() + 1;
                    LocalDate realDay = startDate.plusDays(daysShift)
                            .plusWeeks(weekNum - 1)
                            .plusWeeks(weekType)
                            .plusDays(datum.getDay() - 1);
                    saveAllData(weekType, datum, realDay, (long) weekNum + weekType + 1);
                }
                log.info("Added week number " + (weekNum + weekType + 1));
            }
        }
        return schedule;
    }

    private void initializeFirstCycle(List<Datum> datumList, LocalDate startDate, int maxCycleWeekNumber) {
        initializeFirstWeek(datumList, startDate);
        for (int weekType = 1; weekType < maxCycleWeekNumber; weekType++) {
            List<Datum> weekData = getDatumForCertainWeek(datumList, weekType);
            for (Datum datum : weekData) {

                int daysShift = 7 - startDate.getDayOfWeek().getValue() + 1;
                LocalDate realDay = startDate.plusDays(daysShift)
                        .plusWeeks(weekType - 1)
                        .plusDays(datum.getDay() - 1);
                saveAllData(weekType, datum, realDay, (long) weekType + 1);
            }
            log.info("Added week number " + (weekType + 1));
        }
    }

    private void initializeFirstWeek(List<Datum> datumList, LocalDate startDate) {
        int weekType = 0;
        int dayNumber = startDate.getDayOfWeek().getValue();
        List<Datum> weekData = getDatumForCertainWeekFromCertainDay(datumList, 0, dayNumber);
        for (Datum datum : weekData) {

            LocalDate realDay = startDate.plusWeeks(weekType).minusDays(dayNumber).plusDays(datum.getDay());
            saveAllData(weekType, datum, realDay, 1L);
        }
        log.info("Added week number 1");
    }

    private void saveAllData(int weekType, Datum datum, LocalDate realDay, Long weekNum) {
        AllData allData = new AllData();
        allData.setDate(realDay);

        Pair pair = getPair(datum);
        allData.setPair(pair);

        Room room = roomDao.findAllByName(datum.getRoom().getName());
        allData.setRoom(room);

        Group group = groupDao.findAllByName(datum.getGroup().getName());
        if (group == null) {
            group = new Group();
            group.setName(datum.getGroup().getName());
            groupDao.save(group);
        }
        allData.setGroup(group);

        allData.setWeekType(String.valueOf(weekType)); //TODO:temp. Need table for weeks

        allData.setWeekNum(weekNum);

        allDataDao.save(allData);
    }

    private Pair getPair(Datum datum) {
        return pairDao.findAllByName(datum.getPair().getPairStr().trim());
    }

    private List<TimetableData> getScheduleFromServer() throws IOException {
        return scheduleGetter.getScheduleFromServer();
    }

    private List<Datum> getDatumForCertainWeek(List<Datum> datumList, int weekType) {
        return datumList.stream().filter(d -> d.getWeekNumber() == weekType).collect(Collectors.toList());
    }

    private List<Datum> getDatumForCertainWeekFromCertainDay(List<Datum> datumList, int weekType, int dayNumber) {
        return datumList.stream()
                .filter(d -> d.getWeekNumber() == weekType)
                .filter(d -> d.getDay() >= dayNumber)
                .collect(Collectors.toList());
    }

    private void retrieveDataFromServer() throws IOException {
        schedule = getScheduleFromServer();
    }
}
