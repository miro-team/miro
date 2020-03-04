package miet.rooms.api.service;

import miet.rooms.api.web.response.SchemeConfigMapping;
import miet.rooms.repository.jpa.dao.SchemeDao;
import miet.rooms.repository.jpa.entity.Scheme;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SchemeService {

    private final SchemeDao schemeDao;

    @Autowired
    public SchemeService(SchemeDao schemeDao) {
        this.schemeDao = schemeDao;
    }

    public Scheme getSchemeByFloorAndBuilding(Long floor, String building) {
        return schemeDao.findAllByFloorAndBuilding(floor, building);
    }

    public List<Object> findAllMapping() throws JSONException {
        return schemeDao.findAll().stream()
                .map(r -> SchemeConfigMapping.builder()
                        .id(r.getId())
                        .name(r.getName())
                        .floor(String.valueOf(r.getFloor()))
                        .building(r.getBuilding())
                        .fileName(r.getFileName())
                        .build())
                .collect(Collectors.toList());
    }
}
