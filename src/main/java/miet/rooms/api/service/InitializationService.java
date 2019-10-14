package miet.rooms.api.service;

import lombok.extern.slf4j.Slf4j;
import miet.rooms.initializer.ScheduleGetter;
import miet.rooms.initializer.jsoninitdata.Datum;
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
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
    private final EngageTypeDao engageTypeDao;

    private Map<Long, Room> roomsCache;
    private Map<Long, Pair> pairsCache;
    private Map<String, WeekType> weekTypesCache;
    private Map<String, WeekDay> weekDaysCache;
    private Map<Long, RoomType> roomTypesCache;
    private Map<String, Discipline> disciplinesCache;
    private Map<Long, Teacher> teachersCache;
    private Map<String, Group> groupsCache;
    private Map<String, EngageType> engageTypesCache;

    private Map<Long, Event> eventsCache;

    private List<TimetableData> schedule;

    private long engId;

    private List<Event> events;
    private List<Engagement> engagements;

    @Autowired
    public InitializationService(GroupDao groupDao, EventDao eventDao, EngagementDao engagementDao,
                                 RoomDao roomDao, DisciplineDao disciplineDao, TeacherDao teacherDao,
                                 ScheduleGetter scheduleGetter, SchemeDao schemeDao, PairDao pairDao,
                                 WeekTypeDao weekTypeDao, WeekDayDao weekDayDao, RoomTypeDao roomTypeDao, EngageTypeDao engageTypeDao) {
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
        this.engageTypeDao = engageTypeDao;
    }


    private void removeOldData() {
        engagementDao.deleteAll();
        eventDao.deleteAll();
        groupDao.deleteAll();
        roomDao.deleteAll();
        disciplineDao.deleteAll();
        teacherDao.deleteAll();
        engagementDao.deleteAll();
//        eventDao.clearEngId();
    }

    public void initializeSchedule(LocalDate startDate) throws IOException {
        removeOldData();
        schedule = scheduleGetter.getScheduleFromServer();
        engId = getEngId();
        events = new ArrayList<>();
        engagements = new ArrayList<>();
        initializeRooms();
        initializeGroups();
        initializeDisciplines();
        initializeTeachers();
        initializeEmptyEvents(startDate);
        initializeEvents(startDate);

    }

    private long getEngId() {
        return eventDao.findAll().stream()
                .map(Event::getEngId)
                .max(Long::compareTo)
                .orElse(0L);
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
        roomTypesCache = roomTypeDao.findAll().stream()
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

        teachersCache = teacherDao.findAll().stream()
                .collect(Collectors.toMap(Teacher::getId, teacher -> teacher));

        groupsCache = groupDao.findAll().stream()
                .filter(distinctByKey(Group::getName))
                .collect(Collectors.toMap(Group::getName, group -> group));
    }

    private void initializeEngageTypesCache() {
        engageTypesCache = engageTypeDao.findAll().stream()
                .filter(engageType -> engageType.getName().equals("Лабораторная работа")
                        || engageType.getName().equals("Семинар")
                        || engageType.getName().equals("Лекция")
                        || engageType.getName().equals("Занятие")
                )
                .collect(Collectors.toMap(engageType -> {
                    switch (engageType.getName()) {
                        case "Лабораторная работа":
                            return "Лаб";
                        case "Лекция":
                            return "Лек";
                        case "Семинар":
                            return "Пр";
                        default:
                            return "Зан";
                    }
                }, engageType -> engageType));
    }

    private void initializeGroups() {
        List<Group> groups = scheduleGetter.getGroups().stream().map(str -> {
            Group group = new Group();
            group.setName(str.trim());
            return group;
        }).collect(Collectors.toList());
        groupDao.saveAll(groups);
    }

    private void initializeDisciplines() {
        initializeEngageTypesCache();

        List<Discipline> disciplines = schedule.stream()
                .flatMap(ttl -> ttl.getData().stream())
                .map(Datum::getDiscipline)
                .map(d -> {
                    Discipline discipline = new Discipline();

                    String disciplineName = d.getName().trim();
                    String disciplineTypeStr = "Зан";

                    Pattern p = Pattern.compile("\\[*]");
                    Matcher m = p.matcher(d.getName().trim());
                    if (m.find()) {
                        disciplineName = p.matcher(d.getName().trim()).replaceAll("");
//                        disciplineTypeStr = p.matcher(d.getName().trim()).group(0);
                    }

                    discipline.setTitle(disciplineName);
                    discipline.setCode(d.getCode().trim());
                    discipline.setDisType(engageTypesCache.get(disciplineTypeStr));
                    return discipline;
                })
                .filter(distinctByKey(Discipline::getCode))
                .collect(Collectors.toList());
        disciplineDao.saveAll(disciplines);

        initializeDisciplinesCache();
    }

    public static <T> Predicate<T> distinctByKey(
            Function<? super T, ?> keyExtractor) {

        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    private void initializeDisciplinesCache() {
        disciplinesCache = disciplineDao.findAll().stream()
                .collect(Collectors.toMap(Discipline::getCode, discipline -> discipline));
    }

    private void initializeTeachers() {
        List<Teacher> disciplines = schedule.stream()
                .flatMap(ttl -> ttl.getData().stream())
                .map(datum -> datum.getDiscipline().getTeacherFull())
                .distinct()
                .map(d -> {
                    String[] teacherFio = d.trim().split(" ");
                    Teacher teacher = Teacher.builder()
                            .name(teacherFio[1])
                            .surname(teacherFio[0])
                            .build();
                    try {
                        teacher.setPatronymic(teacherFio[2]);
                    } catch (Exception ex) {
                        teacher.setPatronymic("");
                    }
                    return teacher;
                }).collect(Collectors.toList());
        teacherDao.saveAll(disciplines);
    }

    private void initializeEmptyEvents(LocalDate startDate) {
        initializeCache();

        List<Event> events = new ArrayList<>();

        LocalDate realDate = initializeFirstEmptyCycle(startDate);
        realDate = initializeEmptyCycles(3, realDate);
        initializeLastEmptyWeeks(2, realDate);

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

    private void initializeLastEmptyWeeks(int weeksAmount, LocalDate realDate) {
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

    private void initializeEvents(LocalDate startDate) {
        initializeEventsCache();

        initializeFirstCycle(1);
        for (int cycleNum = 2; cycleNum <= 5; cycleNum++) {
            initializeCycles(cycleNum);
        }

//        for (Event event : events) {
//            eventDao.update(event.getEngId(), event.getDate(), event.getRoom().getId(), event.getPair().getId());
//        }
        eventDao.saveAll(events);
        engagementDao.saveAll(engagements);
    }

    private void initializeFirstCycle(int weekCycle) {
        for (TimetableData ttd : schedule) {
            for (Datum datum : ttd.getData()) {
                Event event = getEvent(datum, weekCycle);
                if (event != null) {
                    if (wasEngaged(datum)) continue;

                    Engagement engagement = Engagement.builder()
                            .discipline(disciplinesCache.get(datum.getDiscipline().getCode().trim()))
                            .group(groupsCache.get(datum.getGroup().getName().trim()))
                            .teacher(getTeacherByFullName(datum.getDiscipline().getTeacherFull()))
                            .engId(engId)
                            .build();

                    event.setEngId(engId++);

                    events.add(event);
                    engagements.add(engagement);
                }
            }
        }
    }

    private void initializeCycles(int weekCycle) {
        for (TimetableData ttd : schedule) {
            for (Datum datum : ttd.getData()) {
                Event event = getEvent(datum, weekCycle);
                if (event != null) {
                    event.setEngId(getEngIdFromLastEvent(event, datum));
                    events.add(event);
                    engId++;
                    events.add(event);
                }
            }
        }
    }

    private Long getEngIdFromLastEvent(Event currentEvent, Datum datum) {
        return events.stream()
                .filter(event -> event.getPair().getOrder().equals(getPairByPairNum(Long.parseLong(datum.getPair().getPairStr().substring(0, 1))).getOrder())
                        && event.getRoom().getName().trim().equals(getRoomByName(datum.getRoom().getName()).getName().trim())
                        && event.getWeekDay().getOrderNum().equals(getWeekDay((int) datum.getDay()).getOrderNum())
                        && event.getDate().equals(currentEvent.getDate().minusWeeks(4))
                )
                .map(Event::getEngId)
                .findFirst().orElse(engId);
    }

    private boolean wasEngaged(Datum datum) {
        Long engId = checkEngageExists(datum);
        if (engId != null) {
            Engagement engagement = Engagement.builder()
                    .discipline(disciplinesCache.get(datum.getDiscipline().getCode().trim()))
                    .group(groupsCache.get(datum.getGroup().getName().trim()))
                    .teacher(getTeacherByFullName(datum.getDiscipline().getTeacherFull()))
                    .engId(engId)
                    .build();
            engagements.add(engagement);
            return true;
        }
        return false;
    }

    private Long checkEngageExists(Datum datum) {
        Event checkingEvent = events.stream()
                .filter(event -> event.getWeekNum().equals(datum.getWeekNumber() + 1)
                        && event.getPair().getOrder().equals(getPairByPairNum(Long.parseLong(datum.getPair().getPairStr().substring(0, 1))).getOrder())
                        && event.getRoom().getName().trim().equals(getRoomByName(datum.getRoom().getName()).getName().trim())
                        && event.getWeekDay().getOrderNum().equals(getWeekDay((int) datum.getDay()).getOrderNum())
                        && event.getWeekType().getWeekTypeCode().equals(String.valueOf(datum.getWeekNumber() + 1))
                ).findFirst().orElse(null);
        if (checkingEvent != null && checkingEvent.getEngId() != null)
            return checkingEvent.getEngId();
        return engId++;
    }

    private Event getEvent(Datum datum, long weekCycle) {
        long weekType = datum.getWeekNumber();
        Long weekNumber = (weekCycle - 1) * 4 + weekType + 1;
        return eventsCache.values().stream()
                .filter(event -> event.getWeekNum().equals(weekNumber)
                        && event.getPair().getOrder().equals(getPairByPairNum(Long.parseLong(datum.getPair().getPairStr().substring(0, 1))).getOrder())
                        && event.getRoom().getName().trim().equals(getRoomByName(datum.getRoom().getName()).getName().trim())
                        && event.getWeekDay().getOrderNum().equals(getWeekDay((int) datum.getDay()).getOrderNum())
                        && event.getWeekType().getWeekTypeCode().equals(getWeekType(weekNumber.intValue()).getWeekTypeCode())
                )
                .findFirst().orElse(null);
    }

    private void initializeEventsCache() {
        eventsCache = eventDao.findAll().stream()
                .collect(Collectors.toMap(Event::getId, event -> event));
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
        return roomTypesCache.get(3L);
    }

    private Pair getPairByPairNum(Long pairNum) {
        return pairsCache.values().stream()
                .filter(pair -> pair.getOrder().equals(pairNum))
                .findFirst()
                .orElse(null);
    }

    private Room getRoomByName(String roomName) {
        return roomsCache.values().stream()
                .filter(room -> room.getName().trim().equals(roomName.trim()))
                .findFirst()
                .orElse(null);
    }

    private Teacher getTeacherByFullName(String fullName) {
        String name = fullName.split(" ")[0];
        String surname = fullName.split(" ")[1];
        return teachersCache.values().stream()
                .filter(teacher -> teacher.getName().equals(name)
                        && teacher.getSurname().equals(surname))
                .findFirst()
                .orElse(null);
    }
}
