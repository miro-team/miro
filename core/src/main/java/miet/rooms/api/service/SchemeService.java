package miet.rooms.api.service;

import miet.rooms.api.web.response.SchemeConfigMapping;
import miet.rooms.repository.jpa.dao.SchemeDao;
import miet.rooms.repository.jpa.entity.Scheme;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Map<Long, Object> findAll() throws JSONException {
        Map<Long, Object> schemeConfig = new HashMap<>();
        List<Scheme> schemes = schemeDao.findAll();
        for(Scheme scheme : schemes) {
            SchemeConfigMapping schemeConfigMapping = SchemeConfigMapping.builder()
                    .name(scheme.getName())
                    .building(scheme.getBuilding())
                    .fileName(scheme.getFileName())
                    .floor(scheme.getFloor())
                    .build();
            schemeConfig.put(scheme.getId(), schemeConfigMapping);
        }
        return schemeConfig;
    }
}
