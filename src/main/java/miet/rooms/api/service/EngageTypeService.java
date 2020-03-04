package miet.rooms.api.service;

import miet.rooms.api.web.response.ConfigResponse;
import miet.rooms.repository.jpa.dao.EngageTypeDao;
import org.json.JSONException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EngageTypeService {
    private final EngageTypeDao engageTypeDao;

    public EngageTypeService(EngageTypeDao engageTypeDao) {
        this.engageTypeDao = engageTypeDao;
    }

    public List<Object> findAllMapping() throws JSONException {
        return engageTypeDao.findAll().stream()
                .map(r -> {
                    ConfigResponse response = new ConfigResponse();
                    response.setId(r.getId());
                    response.setName(r.getName());
                    return response;
                })
                .collect(Collectors.toList());
    }

}
