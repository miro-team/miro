//package miet.rooms.api.schedule.data.database.service;
//
//import miet.rooms.api.schedule.data.frontdata.Event;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//@Service
//public class PrimaryScheduleService {
//
//    @Autowired
//    private PrimaryScheduleDao primaryScheduleDao;
//
//    @Transactional
//    public void save(Event event) {
//        PrimarySchedule primarySchedule = new PrimarySchedule();
//        primarySchedule.setEventId(event.getEventId());
//        primarySchedule.setEngagedBy(event.getEngagedBy());
//        primarySchedule.setTeacherName(event.getTeacherName());
//        primarySchedule.setDiscipline(event.getDiscipline());
//        primaryScheduleDao.save(primarySchedule);
//    }
//
//    public void save(List<Event> events) {
//        for(Event event : events) {
//            save(event);
//        }
//    }
//
//    @Transactional
//    public PrimarySchedule getByEventId(String eventId) {
//        return primaryScheduleDao.getAllByEventId(eventId);
//    }
//}
