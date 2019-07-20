package miet.rooms.api.service;

import miet.rooms.repository.dao.WeekDayDao;
import miet.rooms.repository.entity.WeekDay;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
//@ComponentScan(basePackages = {"miet.rooms.repository.dao.*"})
public class WeekDayService {
    @Autowired
    private WeekDayDao weekDayDao;

    public Map<Long, String> findAll() throws JSONException {
        Map<Long, String> weekDays = new HashMap<>();
        List<WeekDay> weekDayList = weekDayDao.findAll();
        for(WeekDay weekDay : weekDayList) {
            weekDays.put(weekDay.getId(), weekDay.getWeekDayName());
        }
        return weekDays;
    }
}
