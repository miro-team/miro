package miet.rooms.api.service;

import miet.rooms.repository.jpa.dao.SchemeDao;
import miet.rooms.repository.jpa.entity.Scheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SchemeService {

    private final SchemeDao schemeDao;

    @Autowired
    public SchemeService(SchemeDao schemeDao) {
        this.schemeDao = schemeDao;
    }

    public Scheme getSchemeByName(Long floor, String building) {
        return schemeDao.findAllByFloorAndBuilding(floor, building);
    }
}
