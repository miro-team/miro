package miet.rooms.initializer.controller;

import lombok.extern.slf4j.Slf4j;
import miet.rooms.initializer.ScheduleGetter;
import miet.rooms.initializer.initdata.Datum;
import miet.rooms.initializer.initdata.TimetableData;
import miet.rooms.repository.dao.*;
import miet.rooms.repository.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
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

    @Autowired
    private RoomTypeDao roomTypeDao;

    @Autowired
    private SchemeDao schemeDao;

    @Autowired
    private EngageTypeDao engageTypeDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CycleEventDao cycleEventDao;

    private List<TimetableData> schedule;

    @PostMapping(value = "/all")
    public List<TimetableData> initializeAll(
            @RequestParam(value = "weekAmount") Long weekAmount,
            @RequestParam(value = "startDate") @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate startDate) throws IOException {
        jdbcTemplate.execute("truncate table schedule.all_data cascade ;" +
                "truncate table locations.rooms cascade;" +
                "truncate table data.edu_groups cascade ;");
        retrieveDataFromServer();
        initializeRooms();
        initializeGroups();
        initializeEmptySchedule(startDate, weekAmount);
        return initializeSchedule(weekAmount, startDate);
    }

    private void initializeEmptySchedule(LocalDate startDate, Long weekAmount) {
        List<String> roomNames = scheduleGetter.getRooms();
        LocalDate realDay = startDate;
        int maxWeekTypesAmount = cycleEventDao.findAll().size(); //TODO: rename table to weekType and use it as foreign key
        int totalCycleNum = (int) (weekAmount / maxWeekTypesAmount);
        for (int cycleNum = 0; cycleNum < totalCycleNum; cycleNum++) {
            for (int weekType = 0; weekType < maxWeekTypesAmount; weekType++) {
                DayOfWeek dayOfWeek = realDay.getDayOfWeek();
                while (dayOfWeek != DayOfWeek.SUNDAY) {
                    for (String roomName : roomNames) {
                        for (long pairOrder = 1; pairOrder <= 7; pairOrder++) {
                            int weekNum = weekType + maxWeekTypesAmount * cycleNum + 1;
                            saveAllEmptyData(weekType, pairOrder, roomName, realDay, (long) weekNum);
                            log.info("Empty week number = " + weekNum + ", room name = " + roomName + ", day of week = " + dayOfWeek);
                        }
                    }
                    realDay = realDay.plusDays(1);
                    dayOfWeek = realDay.getDayOfWeek();
                }
                realDay = realDay.plusDays(1);
                log.info("Added empty week number " + (weekType + maxWeekTypesAmount * cycleNum + 1));
            }
        }
    }

    @PostMapping(value = "/rooms")
    public void initializeRooms() {
        List<Room> rooms = scheduleGetter.getRooms().stream()
                .map(str -> {
                    Room room = new Room();
                    room.setName(str.trim());
                    room.setCapacity(40L);
                    room.setRoomType(roomTypeDao.findAllById(5L));

                    Scheme scheme = null;
                    switch (str) {
                        case "УВЦ":
                            scheme = schemeDao.findAllByFloorAndBuilding(3L, 3L);
                            break;
                        case "5101":
                            scheme = schemeDao.findAllByFloorAndBuilding(1L, 5L);
                            break;
                        default:
                            try {
                                Long floor = Long.parseLong(String.valueOf(str.trim().charAt(1)));
                                Long building = Long.parseLong(String.valueOf(str.trim().charAt(0)));
                                scheme = schemeDao.findAllByFloorAndBuilding(floor, building);
                            } catch (Exception ex) {
                                log.error(ex.getMessage());
                            }

                    }
                    room.setScheme(scheme);
                    return room;
                }).collect(Collectors.toList());
        roomDao.saveAll(rooms);
    }

    @PostMapping(value = "/groups")
    public void initializeGroups() {
        List<Group> groups = scheduleGetter.getGroups().stream().map(str -> {
            Group group = new Group();
            group.setName(str.trim());
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
        if(schedule == null) {
            retrieveDataFromServer();
        }
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
                    saveAllData(datum, realDay);
                }
                log.info("Added week number " + (weekNum + weekType + 1));
            }
        }
        return schedule;
    }

    @PostMapping(value = "/inside")
    public void retrieveRoomsAndSchemes() throws IOException {
        if(schedule == null) {
            retrieveDataFromServer();
        }
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
                saveAllData(datum, realDay);
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
            saveAllData(datum, realDay);
        }
        log.info("Added week number 1");
    }

    private void saveAllData(Datum datum, LocalDate realDay) {
        Pair pair = getPair(datum);
        Room room = roomDao.findAllByName(datum.getRoom().getName().trim());

        AllData allData = allDataDao.findAllByDateAndAndPair_IdAndRoom_Id(realDay, pair.getId(), room.getId()).get(0);

        Group group = groupDao.findAllByName(datum.getGroup().getName().trim());
        if(allData.getGroup() != null) {
            allData = copyAllData(allData);
        } else {
            allData.setGroup(group);
        }

        allDataDao.save(allData);
    }

    private AllData copyAllData(AllData allData) {
        AllData addedAllData = new AllData();
        addedAllData.setWeekDay(allData.getWeekDay());
        addedAllData.setWeekNum(allData.getWeekNum());
        addedAllData.setEngageType(allData.getEngageType());
        addedAllData.setDate(allData.getDate());
        addedAllData.setPair(allData.getPair());
        addedAllData.setRoom(allData.getRoom());
        addedAllData.setGroup(allData.getGroup());
        addedAllData.setWeekType(allData.getWeekType());
        allData = addedAllData;
        return allData;
    }

    private void saveAllEmptyData(long weekType, Long pairOrder, String roomName, LocalDate realDay, Long weekNum) {
        AllData allData = new AllData();
        allData.setDate(realDay);

        Pair pair = pairDao.findAllById(pairOrder); //TODO: make and use order instead of id
        allData.setPair(pair);

        Room room = roomDao.findAllByName(roomName.trim());
        allData.setRoom(room);

        allData.setWeekType(weekType); //TODO:temp. Need table for weeks

        allData.setWeekNum(weekNum);

        EngageType engageType = engageTypeDao.findAllByDescription("Common");
        allData.setEngageType(engageType);

        allData.setWeekDay((long) realDay.getDayOfWeek().getValue());

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
