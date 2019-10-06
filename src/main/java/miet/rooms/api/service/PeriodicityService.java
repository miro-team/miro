package miet.rooms.api.service;

import miet.rooms.api.web.response.PeriodicityConfigResponse;
import miet.rooms.repository.jpa.dao.PeriodicityDao;
import miet.rooms.repository.jpa.entity.Periodicity;
import org.json.JSONException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PeriodicityService {

    private final PeriodicityDao periodicityDao;

    public PeriodicityService(PeriodicityDao periodicityDao) {
        this.periodicityDao = periodicityDao;
    }

    public List<Object> findAllMapping() throws JSONException {
        return periodicityDao.findAll().stream()
                .map(r -> PeriodicityConfigResponse.builder()
                        .id(r.getId())
                        .name(r.getName())
                        .weekTypes(r.getWeekTypes())
                        .build()
                )
                .collect(Collectors.toList());
    }

    public List<Integer> getWeekTypesIdById(Long id) {
        Periodicity periodicity = periodicityDao.findAllById(id);
        return Arrays.asList(periodicity.getWeekTypes());
    }
}
