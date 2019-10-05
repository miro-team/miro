package miet.rooms.api.service;

import miet.rooms.repository.jpa.dao.EventDao;
import miet.rooms.repository.jpa.entity.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AllDataService {

    private EventDao eventDao;

    @Autowired
    public AllDataService(EventDao eventDao) {
        this.eventDao = eventDao;
    }

    public Long determineCurrentWeek(LocalDate date) {
        Event currentEvent = eventDao.findFirstByDate(date);
        return currentEvent != null ? currentEvent.getWeekNum() : 1L;
    }

    public LocalDate getSemesterStartDate() {
        return eventDao.findFirstByOrderByDateAsc().getDate();
    }

    public Long getLastWeek() {
        return eventDao.findFirstByOrderByWeekNumDesc().getWeekNum();
    }

    public void removeByWeekNum(Long weekNum) {
        eventDao.deleteByWeekNum(weekNum);
    }

    public List<Event> getAllByEngageTypeId(Long engageTypeId) {
//        return eventDao.findAllByEngageType_Id(engageTypeId);
        return null;
    }

    public void save(Event event) {
        eventDao.save(event);
    }

    public Event findById(Long id) {
        return eventDao.findById(id).orElse(null);
    }
}
