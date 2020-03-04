package miet.rooms.api.service;

import miet.rooms.api.web.response.ConfigResponse;
import miet.rooms.repository.jpa.dao.WeekDayDao;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WeekDayService {

    private final WeekDayDao weekDayDao;

    @Autowired
    public WeekDayService(WeekDayDao weekDayDao) {
        this.weekDayDao = weekDayDao;
    }

    public List<Object> findAllMapping() throws JSONException {
        return weekDayDao.findAll().stream()
                .map(r -> {
                    ConfigResponse response = new ConfigResponse();
                    response.setId(r.getId());
                    response.setName(r.getDayCode());
                    return response;
                })
                .collect(Collectors.toList());
    }
}
