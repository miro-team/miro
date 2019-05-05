//package miet.rooms.api.schedule.controller;
//
//import miet.rooms.api.schedule.initializer.TimetableInitializer;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.*;
//
//import java.io.IOException;
//import java.util.List;
//
//@RestController
//@RequestMapping("/data")
//public class PrimarySchedulerController {
//
//    @Autowired
//    private TimetableInitializer timetableInitializer;
//
//    @Autowired
//    private PrimaryScheduleDao primaryScheduleDao;
//
//    @GetMapping(value = "/primary", produces = MediaType.APPLICATION_JSON_VALUE)
//    public @ResponseBody
//    List<PrimarySchedule> getPrimarySchedule() {
//        return primaryScheduleDao.getAllBy();
//    }
//
//    @GetMapping(value = "/initialize", produces = MediaType.APPLICATION_JSON_VALUE)
//    public void initialize() throws IOException {
//        primaryScheduleDao.deleteAll();
//        timetableInitializer.getScheduleFromServer();
//    }
//}
