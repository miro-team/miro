package miet.rooms.api.service;

import miet.rooms.repository.jpa.dao.PeriodicityDao;
import miet.rooms.repository.jpa.entity.Periodicity;
import org.json.JSONException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PeriodicityService {

    private final PeriodicityDao periodicityDao;

    public PeriodicityService(PeriodicityDao periodicityDao) {
        this.periodicityDao = periodicityDao;
    }

    public Map<Long, Object> findAll() throws JSONException {
        Map<Long, Object> periodicityConfig = new HashMap<>();
        List<Periodicity> periodicities = periodicityDao.findAll();
        for (Periodicity periodicity : periodicities) {
            periodicityConfig.put(periodicity.getId(), periodicity.getName());
        }
        return periodicityConfig;
    }

    public List<Integer> getWeekTypesIdById(Long id){
        Periodicity periodicity = periodicityDao.findAllById(id);
        return Arrays.asList(periodicity.getWeekTypes());
    }

    public Periodicity findById(Long id) {
        return periodicityDao.findAllById(id);
    }
}
