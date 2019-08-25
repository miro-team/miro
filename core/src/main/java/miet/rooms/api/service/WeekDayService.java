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

    public Map<Long, Object> findAll() throws JSONException {
        Map<Long, Object> weekConfig = new HashMap<>();
        List<WeekDay> weekDays = weekDayDao.findAll();
        for(WeekDay weekDay : weekDays) {
            weekConfig.put(weekDay.getId(), weekDay.getWeekDayName());
        }
        return weekConfig;
    }

    public WeekDay findAllByOrder(Integer order) {
        return weekDayDao.findAllByOrder(order);
    }
}
