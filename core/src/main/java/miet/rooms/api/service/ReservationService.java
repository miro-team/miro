package miet.rooms.api.service;

import miet.rooms.api.util.DateTimeHelper;
import miet.rooms.api.web.income.ReservationIncome;
import miet.rooms.repository.jdbc.model.UsersReservation;
import miet.rooms.repository.jpa.dao.*;
import miet.rooms.repository.jpa.entity.AllData;
import miet.rooms.repository.jpa.entity.Reservation;
import miet.rooms.security.jpa.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.ws.rs.BadRequestException;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;
    private final AllDataDao allDataDao;
    private final EventTypeDao eventTypeDao;
    private final GroupDao groupDao;
    private final PeriodicityDao periodicityDao;

    public ReservationService(ReservationDao reservationDao, AllDataDao allDataDao, EventTypeDao eventTypeDao,
                              GroupDao groupDao, PeriodicityDao periodicityDao) {
        this.reservationDao = reservationDao;
        this.allDataDao = allDataDao;
        this.eventTypeDao = eventTypeDao;
        this.groupDao = groupDao;
        this.periodicityDao = periodicityDao;
    }

    public List<UsersReservation> getUsersReservations(User user) {
        List<Long> reservationsId = reservationDao.findAllByCreatedBy(user).stream()
                .map(Reservation::getId)
                .collect(Collectors.toList());
        List<AllData> allDataList = allDataDao.findAllByReservation_Id(reservationsId);
        Map<Reservation, List<AllData>> groupedAllData = allDataList.stream()
                .collect(Collectors.groupingBy(AllData::getReservation));
        List<UsersReservation> usersReservations = new ArrayList<>();
        for (Map.Entry<Reservation, List<AllData>> entry : groupedAllData.entrySet()) {
            UsersReservation usersReservation = new UsersReservation();
            usersReservation.setDates(new ArrayList<>());
            for (AllData allData : entry.getValue()) {
                usersReservation.getDates().add(DateTimeHelper.dateToString(allData.getDate()));
            }
            AllData commonAllData = entry.getValue().get(0);
            usersReservation.setId(commonAllData.getId());
            if (commonAllData.getPair() != null) {
                usersReservation.setPair(commonAllData.getPair().getName());
            } else {
                usersReservation.setPair("");
            }
            usersReservation.setRoom(commonAllData.getRoom().getName());
            usersReservation.setWeekNum(commonAllData.getWeekNum());
            usersReservation.setWeekDay(commonAllData.getWeekDay().getWeekDayName());
            usersReservation.setRoomType(commonAllData.getRoom().getRoomType().getName());
            usersReservation.setCapacity(commonAllData.getRoom().getCapacity());
            if(commonAllData.getRoom().getScheme() != null) {
                usersReservation.setBuilding(commonAllData.getRoom().getScheme().getBuilding());
                usersReservation.setFloor(commonAllData.getRoom().getScheme().getFloor().toString());
            }
            usersReservation.setEventType(commonAllData.getReservation().getEventType().getName());
            if(commonAllData.getReservation().getGroup() != null) {
                usersReservation.setGroup(commonAllData.getReservation().getGroup().getName());
            }
            if(commonAllData.getReservation().getTeacher() != null) {
                usersReservation.setTeacher(commonAllData.getReservation().getTeacher());
            }
            usersReservation.setPeriodicity(commonAllData.getReservation().getPeriodicity().getName());

            usersReservations.add(usersReservation);
        }

        return usersReservations;
    }

    @Transactional
    public void makeReservation(ReservationIncome reservationIncome) {
        Reservation reservation = Reservation.builder()
                .eventType(eventTypeDao.findAllById(reservationIncome.getEventTypeId()))
                .createdBy(reservationIncome.getUser())
                .createdAt(LocalDate.now())
                .group(groupDao.findAllById(reservationIncome.getGroupId()))
                .teacher(reservationIncome.getTeacher())
                .periodicity(periodicityDao.findAllById(reservationIncome.getPeriodicityId()))
                .build();
        reservationDao.save(reservation);
        List<AllData> allDataList = allDataDao.findAllById(reservationIncome.getAllDataId());
        for(AllData allData : allDataList) {
            if(allData.getEngaged()) throw new RuntimeException();
        }
        allDataList.forEach(a -> {
            a.setIsEngaged(true);
            a.setReservation(reservation);
        });
        allDataDao.saveAll(allDataList);
    }

    @Transactional
    public void cancelReservation(Long reservationId) {
        reservationDao.deleteAllById(reservationId);
        setRoomsFree(reservationId);
        allDataDao.deleteAllByReservation_Id(reservationId);
    }

    private void setRoomsFree(Long reservationId) {
        List<AllData> allDataList = allDataDao.findAllIdByReservation_Id(reservationId);
        allDataList.forEach(a -> a.setIsEngaged(false));
    }
}
