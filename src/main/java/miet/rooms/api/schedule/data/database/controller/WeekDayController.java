package miet.rooms.api.schedule.data.database.controller;

import miet.rooms.api.schedule.data.database.dao.WeekDayDao;
import miet.rooms.api.schedule.data.database.entity.WeekDay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/week-day")
public class WeekDayController {

    @Autowired
    private WeekDayDao weekDayDao;

    @GetMapping("all")
    public List<WeekDay> getAll() {
        return weekDayDao.findAll();
    }

    @GetMapping("by-id")
    public WeekDay findWeekDayById(@RequestParam Long id) {
        return weekDayDao.findAllById(id);
    }
}
