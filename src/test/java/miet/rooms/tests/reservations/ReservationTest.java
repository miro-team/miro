//package miet.rooms.tests.reservations;
//
//import lombok.extern.slf4j.Slf4j;
//import miet.rooms.Application;
//import miet.rooms.api.service.*;
//import miet.rooms.api.util.DateTimeHelper;
//import miet.rooms.api.web.controller.FilterController;
//import miet.rooms.api.web.income.ReservationIncome;
//import miet.rooms.repository.jpa.entity.Event;
//import miet.rooms.repository.jpa.entity.Group;
//import miet.rooms.repository.jpa.entity.Room;
//import miet.rooms.repository.jpa.entity.WeekType;
//import miet.rooms.security.jpa.entity.User;
//import miet.rooms.security.service.UserService;
//import miet.rooms.tests.configuration.TestConfig;
//import miet.rooms.tests.initializer.PostgreSQLContainerInitializer;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.jupiter.api.Assertions;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.time.DayOfWeek;
//import java.time.LocalDate;
//import java.util.Arrays;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = {Application.class})
//@ContextConfiguration(initializers = {PostgreSQLContainerInitializer.class}, classes = {TestConfig.class})
//@Slf4j
//public class ReservationTest {
//
//    private static boolean initialized = false;
//
//    @Autowired
//    private FilterController filterController;
//
//    @Autowired
//    private SchemeService schemeService;
//
//    @Autowired
//    private RoomTypeService roomTypeService;
//
//    @Autowired
//    private RoomService roomService;
//
//    @Autowired
//    private GroupService groupService;
//
//    @Autowired
//    private PairService pairService;
//
//    @Autowired
//    private WeekTypeService weekTypeService;
//
//    @Autowired
//    private WeekDayService weekDayService;
//
//    @Autowired
//    private AllDataService allDataService;
//
//    @Autowired
//    private ReservationService reservationService;
//
//    @Autowired
//    private UserService userService;
//
//    @Before
//    public void initializeTables() {
//        if (!initialized) {
//            initializeRooms();
//            initializeGroups();
//            initializeAllData();
//            initializeUsers();
//            initialized = true;
//        }
//    }
//
//    private void initializeRooms() {
//        log.info("Start initializing rooms");
//        Room room1 = Room.builder()
//                .name("1201")
//                .capacity(40L)
//                .scheme(schemeService.getSchemeByFloorAndBuilding(1L, "1"))
//                .roomType(roomTypeService.findAllById(1L))
//                .build();
//        Room room2 = Room.builder()
//                .name("1202")
//                .capacity(40L)
//                .scheme(schemeService.getSchemeByFloorAndBuilding(1L, "1"))
//                .roomType(roomTypeService.findAllById(1L))
//                .build();
//        Room room3 = Room.builder()
//                .name("3101")
//                .capacity(40L)
//                .scheme(schemeService.getSchemeByFloorAndBuilding(1L, "3"))
//                .roomType(roomTypeService.findAllById(3L))
//                .build();
//        Room room4 = Room.builder()
//                .name("3102")
//                .capacity(40L)
//                .scheme(schemeService.getSchemeByFloorAndBuilding(1L, "3"))
//                .roomType(roomTypeService.findAllById(3L))
//                .build();
//        Room room5 = Room.builder()
//                .name("3103")
//                .capacity(40L)
//                .scheme(schemeService.getSchemeByFloorAndBuilding(1L, "3"))
//                .roomType(roomTypeService.findAllById(5L))
//                .build();
//        Room room6 = Room.builder()
//                .name("3201")
//                .capacity(40L)
//                .scheme(schemeService.getSchemeByFloorAndBuilding(2L, "3"))
//                .roomType(roomTypeService.findAllById(3L))
//                .build();
//        Room room7 = Room.builder()
//                .name("3202")
//                .capacity(40L)
//                .scheme(schemeService.getSchemeByFloorAndBuilding(2L, "3"))
//                .roomType(roomTypeService.findAllById(3L))
//                .build();
//        Room room8 = Room.builder()
//                .name("3203")
//                .capacity(40L)
//                .scheme(schemeService.getSchemeByFloorAndBuilding(2L, "3"))
//                .roomType(roomTypeService.findAllById(5L))
//                .build();
//        Room room9 = Room.builder()
//                .name("3301")
//                .capacity(40L)
//                .scheme(schemeService.getSchemeByFloorAndBuilding(3L, "3"))
//                .roomType(roomTypeService.findAllById(3L))
//                .build();
//        Room room10 = Room.builder()
//                .name("4101")
//                .capacity(40L)
//                .scheme(schemeService.getSchemeByFloorAndBuilding(1L, "4"))
//                .roomType(roomTypeService.findAllById(3L))
//                .build();
//        Room room11 = Room.builder()
//                .name("4102")
//                .capacity(40L)
//                .scheme(schemeService.getSchemeByFloorAndBuilding(1L, "4"))
//                .roomType(roomTypeService.findAllById(3L))
//                .build();
//        Room room12 = Room.builder()
//                .name("4103")
//                .capacity(40L)
//                .scheme(schemeService.getSchemeByFloorAndBuilding(1L, "4"))
//                .roomType(roomTypeService.findAllById(5L))
//                .build();
//        Room room13 = Room.builder()
//                .name("4201")
//                .capacity(40L)
//                .scheme(schemeService.getSchemeByFloorAndBuilding(2L, "4"))
//                .roomType(roomTypeService.findAllById(3L))
//                .build();
//        Room room14 = Room.builder()
//                .name("4202")
//                .capacity(40L)
//                .scheme(schemeService.getSchemeByFloorAndBuilding(2L, "4"))
//                .roomType(roomTypeService.findAllById(3L))
//                .build();
//        Room room15 = Room.builder()
//                .name("4301")
//                .capacity(40L)
//                .scheme(schemeService.getSchemeByFloorAndBuilding(3L, "4"))
//                .roomType(roomTypeService.findAllById(3L))
//                .build();
//
//        roomService.saveAll(Arrays.asList(room1, room2, room3, room4, room5, room6, room7, room8, room9, room10,
//                room11, room12, room13, room14, room15));
//        log.info("Stop initializing rooms");
//    }
//
//    private void initializeGroups() {
//        Group group1 = Group.builder()
//                .name("ПИН-31")
//                .build();
//        Group group2 = Group.builder()
//                .name("ПИН-32")
//                .build();
//        Group group3 = Group.builder()
//                .name("ПИН-33")
//                .build();
//        Group group4 = Group.builder()
//                .name("БТС-11")
//                .build();
//        Group group5 = Group.builder()
//                .name("БТС-12")
//                .build();
//        Group group6 = Group.builder()
//                .name("БТС-13")
//                .build();
//        Group group7 = Group.builder()
//                .name("БТС-22")
//                .build();
//        Group group8 = Group.builder()
//                .name("ПИН-11")
//                .build();
//        Group group9 = Group.builder()
//                .name("ПИН-12")
//                .build();
//        Group group10 = Group.builder()
//                .name("ПИН-44")
//                .build();
//        Group group11 = Group.builder()
//                .name("ПИН-45")
//                .build();
//        Group group12 = Group.builder()
//                .name("ПМ-11")
//                .build();
//        Group group13 = Group.builder()
//                .name("ПМ-12")
//                .build();
//        Group group14 = Group.builder()
//                .name("ЭКТ-21")
//                .build();
//        Group group15 = Group.builder()
//                .name("Д-11")
//                .build();
//        Group group16 = Group.builder()
//                .name("ИН-22")
//                .build();
//        groupService.saveAll(Arrays.asList(group1, group2, group3, group4, group5, group6, group7, group8,
//                group9, group10, group11, group12, group13, group14, group15, group16));
//    }
//
//    private void initializeAllData() {
//        log.info("Start initializing all data");
//        LocalDate currentLocalDate = DateTimeHelper.asLocalDate("02.09.2019");
//        WeekType ch1WeekType = weekTypeService.getWeekTypeByCodeNum(0L);
//        WeekType ch2WeekType = weekTypeService.getWeekTypeByCodeNum(1L);
//        WeekType zn1WeekType = weekTypeService.getWeekTypeByCodeNum(2L);
//        WeekType zn2WeekType = weekTypeService.getWeekTypeByCodeNum(3L);
//        addWeek(currentLocalDate, 1L, ch1WeekType);
//        addWeek(currentLocalDate.plusWeeks(1), 2L, ch2WeekType);
//        addWeek(currentLocalDate.plusWeeks(2), 3L, zn1WeekType);
//        addWeek(currentLocalDate.plusWeeks(3), 4L, zn2WeekType);
//        addWeek(currentLocalDate.plusWeeks(4), 5L, ch1WeekType);
//        addWeek(currentLocalDate.plusWeeks(5), 6L, ch2WeekType);
//        addWeek(currentLocalDate.plusWeeks(6), 7L, zn1WeekType);
//        addWeek(currentLocalDate.plusWeeks(7), 8L, zn2WeekType);
//        addWeek(currentLocalDate.plusWeeks(8), 9L, ch1WeekType);
//        addWeek(currentLocalDate.plusWeeks(9), 10L, ch2WeekType);
//        addWeek(currentLocalDate.plusWeeks(10), 11L, zn1WeekType);
//        addWeek(currentLocalDate.plusWeeks(11), 12L, zn2WeekType);
//        addWeek(currentLocalDate.plusWeeks(12), 13L, ch1WeekType);
//        addWeek(currentLocalDate.plusWeeks(13), 14L, ch2WeekType);
//        addWeek(currentLocalDate.plusWeeks(14), 15L, zn1WeekType);
//        addWeek(currentLocalDate.plusWeeks(15), 16L, zn2WeekType);
//        log.info("Stop initializing rooms");
//    }
//
//    private void initializeUsers() {
//        User user = User.builder()
//                .userLogin("me")
//                .password("me")
//                .name("me")
//                .surname("me")
//                .patronymic("me")
//                .build();
//        userService.save(user);
//    }
//
//    private void addWeek(LocalDate currentLocalDate, Long weekNum, WeekType weekType) {
//        log.info("Initializing week number {}", weekNum);
//        int workdays = 5;
//        int addedDays = 0;
//        while (addedDays < workdays) {
//            if (!(currentLocalDate.getDayOfWeek() == DayOfWeek.SATURDAY ||
//                    currentLocalDate.getDayOfWeek() == DayOfWeek.SUNDAY)) {
//                for (Room room : roomService.findAll()) {
//                    for (int pairNum = 1; pairNum < 3; pairNum++) {
//                        Event event = Event.builder()
//                                .date(currentLocalDate)
//                                .pair(pairService.getPairByOrder(pairNum))
//                                .group(null)
//                                .weekType(weekType)
//                                .room(room)
//                                .weekNum(weekNum)
//                                .engageType(null)
//                                .weekDay(weekDayService.findAllByOrder(currentLocalDate.getDayOfWeek().getValue()))
//                                .isEngaged(false)
//                                .reservation(null)
//                                .build();
//                        allDataService.save(event);
//                    }
//                }
//
//                ++addedDays;
//            }
//        }
//    }
//
//    @Test
//    public void cannotMakeReservationOnEngagedRoom() {
//        Event event = allDataService.findById(1L);
//        event.setGroup(groupService.findById(1L));
//        event.setIsEngaged(true);
//        allDataService.save(event);
//        ReservationIncome reservationIncome = ReservationIncome.builder()
//                .user(userService.getUserByUsername("me"))
//                .eventTypeId(1L)
//                .periodicityId(1L)
//                .allDataId(new Long[]{1L})
//                .build();
//        Assertions.assertThrows(RuntimeException.class, () -> reservationService.makeReservation(reservationIncome));
//    }
//}
