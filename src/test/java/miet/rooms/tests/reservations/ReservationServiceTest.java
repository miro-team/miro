package miet.rooms.tests.reservations;

import lombok.extern.slf4j.Slf4j;
import miet.rooms.Application;
import miet.rooms.api.service.ReservationService;
import miet.rooms.api.util.DateTimeHelper;
import miet.rooms.api.web.income.ReservationIncome;
import miet.rooms.repository.jdbc.model.UsersReservation;
import miet.rooms.repository.jpa.dao.*;
import miet.rooms.repository.jpa.entity.*;
import miet.rooms.security.jpa.dao.UserDao;
import miet.rooms.security.jpa.entity.User;
import miet.rooms.tests.configuration.TestConfig;
import miet.rooms.tests.initializer.PostgreSQLContainerInitializer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {Application.class})
@ContextConfiguration(initializers = {PostgreSQLContainerInitializer.class}, classes = {TestConfig.class})
@Slf4j
class ReservationServiceTest {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private DisciplineDao disciplineDao;

    @Autowired
    private TeacherDao teacherDao;

    @Autowired
    private RoomDao roomDao;

    @Autowired
    private GroupDao groupDao;

    @Autowired
    private EventDao eventDao;

    @Autowired
    private PairDao pairDao;

    @Autowired
    private WeekDayDao weekDayDao;

    @Autowired
    private WeekTypeDao weekTypeDao;

    @Autowired
    private EngageTypeDao engageTypeDao;

    @Autowired
    private SchemeDao schemeDao;

    @Autowired
    private RoomTypeDao roomTypeDao;

    @Autowired
    private EngagementDao engagementDao;

    @Autowired
    private ReservationDao reservationDao;

    private boolean initialized = false;

    @BeforeEach
    void initialize() {
        if (!initialized) {
            initializeSchemes();
            initializerRoomTypes();
            initializeRooms();
            initializeGroups();
            initializeEngageTypes();
            initializeDisciplines();
            initializeTeachers();
            initializeUsers();
            initializeEvents();
            initialized = true;
        }
    }

    void initializeEvents() {
        log.info("Start initializing events");
        eventDao.save(
                Event.builder()
                        .id(1L)
                        .date(DateTimeHelper.asLocalDate("23.10.2019"))
                        .engId(null)
                        .pair(pairDao.findById(1L).orElse(null))
                        .room(roomDao.findById(1L).orElse(null))
                        .weekDay(weekDayDao.findById(1L).orElse(null))
                        .weekNum(8L)
                        .weekType(weekTypeDao.findById(1L).orElse(null))
                        .build()
        );
    }

    private void initializeTeachers() {
        log.info("Start initializing teachers");
        teacherDao.save(
                Teacher.builder()
                        .id(1L)
                        .name("Иванов")
                        .surname("Иван")
                        .patronymic("Иванович")
                        .build()
        );
    }

    private void initializeEngageTypes() {
        engageTypeDao.save(
                EngageType.builder()
                        .id(1L)
                        .name("Лекция")
                        .build()
        );
    }

    private void initializeDisciplines() {
        log.info("Start initializing disciplines");
        disciplineDao.save(
                Discipline.builder()
                        .id(1L)
                        .title("Основы программирования")
                        .code("100500")
                        .disType(
                                engageTypeDao.findById(1L).orElse(null)
                        )
                        .build()
        );
    }

    private void initializeSchemes() {
        schemeDao.save(
                Scheme.builder()
                        .id(1L)
                        .building("1")
                        .floor(2L)
                        .name("1 корпус 2 этаж")
                        .build()
        );
    }

    private void initializerRoomTypes() {
        roomTypeDao.save(
                RoomType.builder()
                        .id(1L)
                        .name("Лекционная")
                        .build()
        );
    }

    private void initializeRooms() {
        log.info("Start initializing rooms");
        Room room = Room.builder()
                .id(1L)
                .name("1201")
                .capacity(40L)
                .scheme(schemeDao.findById(1L).orElse(null))
                .roomType(roomTypeDao.findById(1L).orElse(null))
                .build();

        roomDao.save(room);
    }

    private void initializeGroups() {
        groupDao.saveAll(Arrays.asList(
                Group.builder()
                        .id(1L)
                        .name("ПИН-31")
                        .build(),
                Group.builder()
                        .id(2L)
                        .name("ПИН-32")
                        .build()));
    }

    private void initializeUsers() {
        userDao.saveAll(Arrays.asList(
                User.builder()
                        .id(1L)
                        .userLogin("me")
                        .password("me")
                        .name("me")
                        .surname("me")
                        .patronymic("me")
                        .build(),
                User.builder()
                        .id(2L)
                        .userLogin("me2")
                        .password("me2")
                        .name("me2")
                        .surname("me2")
                        .patronymic("me2")
                        .build()
                )
        );
    }

    @Test
    void nonExistedEventId() {
        assertThrows(RuntimeException.class, () ->
                reservationService.makeReservation(ReservationIncome.builder()
                        .disciplineId(1L)
                        .eventsId(new Long[]{2L})
                        .engageTypeId(1L)
                        .groupsId(new Long[]{1L, 2L})
                        .teacherId(1L)
                        .user(userDao.findById(1L).orElse(null))
                        .build())
        );
    }

    @Test
    void cannotMakeReservationOnEngagedRoom() {
        reservationService.makeReservation(ReservationIncome.builder()
                .disciplineId(1L)
                .eventsId(new Long[]{1L})
                .engageTypeId(1L)
                .groupsId(new Long[]{1L, 2L})
                .teacherId(1L)
                .user(userDao.findById(1L).orElse(null))
                .build());

        assertThrows(RuntimeException.class, () ->
                reservationService.makeReservation(ReservationIncome.builder()
                        .disciplineId(1L)
                        .eventsId(new Long[]{1L})
                        .engageTypeId(1L)
                        .groupsId(new Long[]{1L, 2L})
                        .teacherId(1L)
                        .user(userDao.findById(1L).orElse(null))
                        .build())
        );
    }

    @Test
    void makeReservationForSeveralGroups() {
        reservationService.makeReservation(ReservationIncome.builder()
                .disciplineId(1L)
                .eventsId(new Long[]{1L})
                .engageTypeId(1L)
                .groupsId(new Long[]{1L, 2L})
                .teacherId(1L)
                .user(userDao.findById(1L).orElse(null))
                .build());

        assertEquals(UsersReservation.builder()
                        .building("1")
                        .capacity(40L)
                        .dates(Collections.singletonList("23.10.2019"))
                        .eventType("Лекция")
                        .floor(2L)
                        .groups("ПИН-31, ПИН-32")
                        .pair("1 пара")
                        .room("1201")
                        .roomType("Лекционная")
                        .teacherFio("Иван. И. И.")
                        .weekDay("Пн")
                        .build(),
                reservationService.getUsersReservations(userDao.findById(1L).orElse(null)).get(0));
    }

    @AfterEach
    void dropAll() {
        eventDao.clearEngId();
//        roomDao.deleteAll();
//        roomTypeDao.deleteAll();
//        schemeDao.deleteAll();
        engagementDao.deleteAll();
        reservationDao.deleteAll();
//        groupDao.deleteAll();
//        disciplineDao.deleteAll();
//        engageTypeDao.deleteAll();
//        teacherDao.deleteAll();
//        userDao.deleteAll();
    }
}
