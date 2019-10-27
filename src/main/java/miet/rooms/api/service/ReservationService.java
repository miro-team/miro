package miet.rooms.api.service;

import miet.rooms.api.util.DateTimeHelper;
import miet.rooms.api.web.income.ReservationIncome;
import miet.rooms.repository.jdbc.model.UsersReservation;
import miet.rooms.repository.jpa.dao.*;
import miet.rooms.repository.jpa.entity.*;
import miet.rooms.security.jpa.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private final EventDao eventDao;
    private final GroupDao groupDao;
    private final EngageTypeDao engageTypeDao;
    private final TeacherDao teacherDao;
    private final ReservationDao reservationDao;
    private final EngagementDao engagementDao;
    private final DisciplineDao disciplineDao;

    public ReservationService(EventDao allDataDao, GroupDao groupDao, EngageTypeDao engageTypeDao,
                              TeacherDao teacherDao, ReservationDao reservationDao,
                              EngagementDao engagementDao, DisciplineDao disciplineDao) {
        this.eventDao = allDataDao;
        this.groupDao = groupDao;
        this.engageTypeDao = engageTypeDao;
        this.teacherDao = teacherDao;
        this.reservationDao = reservationDao;
        this.engagementDao = engagementDao;
        this.disciplineDao = disciplineDao;
    }

    public List<UsersReservation> getUsersReservations(User user) {
        List<Reservation> reservations = reservationDao.findAllByCreatedBy(user);
        Map<Long, String> groupsMapping = reservations.stream()
                .collect(Collectors.groupingBy(
                        Reservation::getEngId, Collectors.mapping(r -> r.getGroup().getName(),
                                Collectors.joining(", ")
                        ))
                );

        List<UsersReservation> usersReservations = new ArrayList<>();

        List<Long> engIds = reservations.stream().map(Reservation::getEngId).distinct().collect(Collectors.toList());
        for (Long engId : engIds) {
            Event event = eventDao.findFirstByEngId(engId);
            List<String> dates = eventDao.findAllByEngId(engId).stream()
                    .map(Event::getDate)
                    .sorted(LocalDate::compareTo)
                    .map(DateTimeHelper::dateToString)
                    .collect(Collectors.toList());
            Engagement engagement = engagementDao.findFirstByEngId(engId);
            usersReservations.add(
                    UsersReservation.builder()
                            .room(event.getRoom().getName())
                            .roomType(event.getRoom().getRoomType().getName())
                            .building(event.getRoom().getScheme().getBuilding())
                            .capacity(event.getRoom().getCapacity())
                            .floor(event.getRoom().getScheme().getFloor())
                            .dates(dates)
                            .eventType(engagement.getEngageType().getName())
                            .groups(groupsMapping.get(engId))
                            .pair(event.getPair().getName())
                            .teacherFio(engagement.getTeacher().getFio())
                            .weekDay(event.getWeekDay().getDayCode())
                            .build()
            );
        }

        return usersReservations;
    }

    @Transactional
    public void makeReservation(ReservationIncome reservationIncome) {
        EngageType engageType = engageTypeDao.findAllById(reservationIncome.getEngageTypeId());
        User createdBy = reservationIncome.getUser();
        LocalDateTime createdAt = LocalDateTime.now();
        Teacher teacher = teacherDao.findById(reservationIncome.getTeacherId()).orElse(null);
        Discipline discipline = disciplineDao.findById(reservationIncome.getDisciplineId()).orElse(null);

        Long engId = createEngagementAndGetEngId(reservationIncome.getGroupsId(), teacher, discipline, engageType);

        List<Reservation> reservations = new ArrayList<>();
        for (Long groupId : reservationIncome.getGroupsId()) {
            Group group = groupDao.findById(groupId).orElse(null);
            reservations.add(
                    Reservation.builder()
                            .engageType(engageType)
                            .createdBy(createdBy)
                            .createdAt(createdAt)
                            .discipline(discipline)
                            .group(group)
                            .teacher(teacher)
                            .engId(engId)
                            .build()
            );
        }

        List<Event> events = new ArrayList<>();
        if (!isAllowedToEngage(reservationIncome.getEventsId()))
            throw new RuntimeException();
        for (Long eventId : reservationIncome.getEventsId()) {
            Event event = eventDao.findById(eventId).orElseThrow(RuntimeException::new);
            event.setEngId(engId);
            events.add(event);
        }
        eventDao.saveAll(events);
        reservationDao.saveAll(reservations);
    }

    private boolean isAllowedToEngage(Long[] eventsId) {
        List<Event> events = eventDao.findAllById(eventsId);
        return events.stream().noneMatch(e -> e.getEngId() != null);
    }

    private Long createEngagementAndGetEngId(Long[] groupsId, Teacher teacher, Discipline discipline,
                                             EngageType engageType) {
        Long engId = engagementDao.findLastEngId();
        if (engId == null)
            engId = 1L;

        List<Engagement> engagements = new ArrayList<>();
        for (Long groupId : groupsId) {
            Group group = groupDao.findById(groupId).orElse(null);
            engagements.add(
                    Engagement.builder()
                            .engId(engId)
                            .teacher(teacher)
                            .discipline(discipline)
                            .engageType(engageType)
                            .group(group)
                            .build()
            );
        }
        engagementDao.saveAll(engagements);

        return engId;
    }

    @Transactional
    public void cancelReservation(Long engId) {
        reservationDao.deleteAllByEngId(engId);
        engagementDao.deleteAllByEngId(engId);
        eventDao.clearEngId(engId);
    }
}