package miet.rooms.api.service;

import miet.rooms.repository.jpa.dao.WeekDayDao;
import miet.rooms.repository.jpa.entity.WeekDay;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WeekDayService {

    private final WeekDayDao weekDayDao;

    @Autowired
    public WeekDayService(WeekDayDao weekDayDao) {
        this.weekDayDao = weekDayDao;
    }

    public Map<Long, String> findAll() throws JSONException {
        Map<Long, String> weekDays = new HashMap<>();
        List<WeekDay> weekDayList = weekDayDao.findAll();
        for(WeekDay weekDay : weekDayList) {
            weekDays.put(weekDay.getId(), weekDay.getWeekDayName());
        }
        return weekDays;
    }
}
