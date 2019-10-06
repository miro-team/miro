package miet.rooms.api.service;

import lombok.extern.slf4j.Slf4j;
import miet.rooms.initializer.ScheduleGetter;
import miet.rooms.initializer.jsoninitdata.TimetableData;
import miet.rooms.repository.jpa.dao.*;
import miet.rooms.repository.jpa.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class InitializationService {

    private final GroupDao groupDao;
    private final EventDao eventDao;
    private final EngagementDao engagementDao;
    private final RoomDao roomDao;
    private final DisciplineDao disciplineDao;
    private final TeacherDao teacherDao;
    private final ScheduleGetter scheduleGetter;
    private final SchemeDao schemeDao;
    private final PairDao pairDao;
    private final WeekTypeDao weekTypeDao;
    private final WeekDayDao weekDayDao;
    private final RoomTypeDao roomTypeDao;

    private Map<Long, Room> roomsCache;
    private Map<Long, Pair> pairsCache;
    private Map<String, WeekType> weekTypesCache;
    private Map<String, WeekDay> weekDaysCache;
    private Map<Long, RoomType> roomTypeCache;

    private List<TimetableData> schedule;

    @Autowired
    public InitializationService(GroupDao groupDao, EventDao eventDao, EngagementDao engagementDao,
                                 RoomDao roomDao, DisciplineDao disciplineDao, TeacherDao teacherDao,
                                 ScheduleGetter scheduleGetter, SchemeDao schemeDao, PairDao pairDao,
                                 WeekTypeDao weekTypeDao, WeekDayDao weekDayDao, RoomTypeDao roomTypeDao) {
        this.groupDao = groupDao;
        this.eventDao = eventDao;
        this.engagementDao = engagementDao;
        this.roomDao = roomDao;
        this.disciplineDao = disciplineDao;
        this.teacherDao = teacherDao;
        this.scheduleGetter = scheduleGetter;
        this.schemeDao = schemeDao;
        this.pairDao = pairDao;
        this.weekTypeDao = weekTypeDao;
        this.weekDayDao = weekDayDao;
        this.roomTypeDao = roomTypeDao;
    }


    private void removeOldData() {
        engagementDao.deleteAll();
        eventDao.deleteAll();
        groupDao.deleteAll();
        roomDao.deleteAll();
        disciplineDao.deleteAll();
        teacherDao.deleteAll();
    }

    public void initializeSchedule(LocalDate startDate) throws IOException {
        removeOldData();
        schedule = scheduleGetter.getScheduleFromServer();
        initializeRooms();
//      initializeGroups();
//      initializeDisciplines();
//      initializeTeachers();
        initializeEmptyEvents(startDate);
        initializeEvents();

    }

    private void initializeRooms() {
        initializeRoomTypesCache();
        List<Room> rooms = schedule.stream()
                .flatMap(ttl -> ttl.getData().stream())
                .map(datum -> datum.getRoom().getName())
                .distinct()
                .map(roomStr -> {
                    Room room = new Room();
                    room.setName(roomStr.trim());
                    room.setCapacity(40L);
                    room.setRoomType(getDefaultRoomType());
                    setScheme(roomStr, room);
                    return room;
                }).collect(Collectors.toList());
        roomDao.saveAll(rooms);
    }

    private void initializeRoomTypesCache() {
        roomTypeCache = roomTypeDao.findAll().stream()
                .collect(Collectors.toMap(RoomType::getId, roomType -> roomType));
    }

    private void setScheme(String roomStr, Room room) {
        Scheme scheme = null;
        try {
            if (roomStr.equals("УВЦ")) {
                scheme = schemeDao.findAllByFloorAndBuilding(3L, "3");
                room.setScheme(scheme);
                return;
            }
            if (roomStr.contains("Студ")) {
                scheme = schemeDao.findAllByFloorAndBuilding(2L, "10");
                room.setScheme(scheme);
                return;
            }
            Long floor = Long.parseLong(String.valueOf(roomStr.trim().charAt(1)));
            String building = String.valueOf(roomStr.trim().charAt(0));
            scheme = schemeDao.findAllByFloorAndBuilding(floor, building);
        } catch (Exception e) {
            log.error("Parsing scheme error: ", e);
        }
        room.setScheme(scheme);
    }

    private void initializeCache() {
        roomsCache = roomDao.findAll().stream()
                .collect(Collectors.toMap(Room::getId, room -> room));

        pairsCache = pairDao.findAll().stream()
                .collect(Collectors.toMap(Pair::getId, pair -> pair));

        weekTypesCache = weekTypeDao.findAll().stream()
                .filter(weekType -> weekType.getWeekTypeCode().equals("ch1") ||
                        weekType.getWeekTypeCode().equals("zn1") ||
                        weekType.getWeekTypeCode().equals("ch2") ||
                        weekType.getWeekTypeCode().equals("zn2"))
                .collect(Collectors.toMap(WeekType::getWeekTypeCode, weekType -> weekType));

        weekDaysCache = weekDayDao.findAll().stream()
                .collect(Collectors.toMap(WeekDay::getDayCode, weekDay -> weekDay));
    }

    public void initializeGroups() {
        List<Group> groups = scheduleGetter.getGroups().stream().map(str -> {
            Group group = new Group();
            group.setName(str.trim());
            return group;
        }).collect(Collectors.toList());
        groupDao.saveAll(groups);
    }

    public void initializeDisciplines() {
        List<Discipline> disciplines = schedule.stream()
                .flatMap(ttl -> ttl.getData().stream())
                .map(datum -> datum.getDiscipline().getName())
                .distinct()
                .map(d -> {
                    Discipline discipline = new Discipline();
                    discipline.setTitle(d.trim());
                    return discipline;
                }).collect(Collectors.toList());
        disciplineDao.saveAll(disciplines);
    }

    public void initializeTeachers() {
        List<Teacher> disciplines = schedule.stream()
                .flatMap(ttl -> ttl.getData().stream())
                .map(datum -> datum.getDiscipline().getTeacherFull())
                .distinct()
                .map(d -> {
                    Teacher teacher = new Teacher();
//                    teacher.setName();
                    return teacher;
                }).collect(Collectors.toList());
        teacherDao.saveAll(disciplines);
    }

    private void initializeEmptyEvents(LocalDate startDate) throws IOException {
        initializeCache();

        List<Event> events = new ArrayList<>();

        LocalDate realDate = initializeFirstEmptyCycle(startDate);
        realDate = initializeEmptyCycles(3, realDate);
        initializeLastWeeks(2, realDate);

        eventDao.saveAll(events);
    }

    private LocalDate initializeFirstEmptyCycle(LocalDate startDate) {
        List<Event> events = new ArrayList<>();

        LocalDate realDate = initializeFirstEmptyWeek(startDate);

        for (int weekNum = 2; weekNum <= 4; weekNum++) {
            for (int dayNum = 1; dayNum <= 7; dayNum++) {
                for (Map.Entry<Long, Pair> pair : pairsCache.entrySet()) {
                    for (Map.Entry<Long, Room> room : roomsCache.entrySet()) {
                        Event event = Event.builder()
                                .date(realDate)
                                .pair(pair.getValue())
                                .room(room.getValue())
                                .weekNum((long) weekNum)
                                .weekType(getWeekType(weekNum))
                                .weekDay(getWeekDay(dayNum))
                                .build();
                        events.add(event);
                    }
                }
                realDate = realDate.plusDays(1);
            }
            log.info("Week №{} was initialized", weekNum);
        }
        eventDao.saveAll(events);
        return realDate;
    }

    private LocalDate initializeFirstEmptyWeek(LocalDate realDate) {
        List<Event> events = new ArrayList<>();

        for (int dayNum = realDate.getDayOfWeek().getValue(); dayNum <= 7; dayNum++) {
            for (Map.Entry<Long, Pair> pair : pairsCache.entrySet()) {
                for (Map.Entry<Long, Room> room : roomsCache.entrySet()) {
                    Event event = Event.builder()
                            .date(realDate)
                            .pair(pair.getValue())
                            .room(room.getValue())
                            .weekNum(1L)
                            .weekDay(getWeekDay(dayNum))
                            .weekType(getWeekType(1))
                            .build();
                    events.add(event);
                }
            }
            realDate = realDate.plusDays(1);
        }
        eventDao.saveAll(events);
        log.info("Week №{} was initialized", 1);
        return realDate;
    }

    private LocalDate initializeEmptyCycles(int cyclesAmount, LocalDate realDate) {
        List<Event> events = new ArrayList<>();

        for (int cycleNum = 1; cycleNum <= cyclesAmount; cycleNum++) {
            for (int weekNum = cycleNum * 4 + 1; weekNum <= cycleNum * 4 + 4; weekNum++) {
                for (int dayNum = 1; dayNum <= 7; dayNum++) {
                    for (Map.Entry<Long, Pair> pair : pairsCache.entrySet()) {
                        for (Map.Entry<Long, Room> room : roomsCache.entrySet()) {
                            Event event = Event.builder()
                                    .date(realDate)
                                    .pair(pair.getValue())
                                    .room(room.getValue())
                                    .weekNum((long) weekNum)
                                    .weekDay(getWeekDay(dayNum))
                                    .weekType(getWeekType(weekNum))
                                    .build();
                            events.add(event);
                        }
                    }
                    realDate = realDate.plusDays(1);
                }
                log.info("Week №{} was initialized", weekNum);
            }
        }
        eventDao.saveAll(events);
        return realDate;
    }

    private void initializeLastWeeks(int weeksAmount, LocalDate realDate) {
        List<Event> events = new ArrayList<>();

        for (int weekNum = 17; weekNum < 17 + weeksAmount; weekNum++) {
            for (int dayNum = 1; dayNum <= 7; dayNum++) {
                for (Map.Entry<Long, Pair> pair : pairsCache.entrySet()) {
                    for (Map.Entry<Long, Room> room : roomsCache.entrySet()) {
                        Event event = Event.builder()
                                .date(realDate)
                                .pair(pair.getValue())
                                .room(room.getValue())
                                .weekNum((long) weekNum)
                                .weekDay(getWeekDay(dayNum))
                                .weekType(getWeekType(weekNum))
                                .build();
                        events.add(event);
                    }
                }
                realDate = realDate.plusDays(1);
            }
            log.info("Week №{} was initialized", weekNum);
        }
        eventDao.saveAll(events);
    }

    private void initializeEvents() {

    }

    private WeekType getWeekType(int weekNum) {
        switch (weekNum % 4) {
            case 1:
                return weekTypesCache.get("ch1");
            case 2:
                return weekTypesCache.get("zn1");
            case 3:
                return weekTypesCache.get("ch2");
            default:
                return weekTypesCache.get("zn2");
        }
    }

    private WeekDay getWeekDay(int weekDay) {
        switch (weekDay) {
            case 1:
                return weekDaysCache.get("Пн");
            case 2:
                return weekDaysCache.get("Вт");
            case 3:
                return weekDaysCache.get("Ср");
            case 4:
                return weekDaysCache.get("Чт");
            case 5:
                return weekDaysCache.get("Пт");
            case 6:
                return weekDaysCache.get("Сб");
            default:
                return weekDaysCache.get("Вс");
        }
    }

    private RoomType getDefaultRoomType() {
        return roomTypeCache.get(3L);
    }
}
