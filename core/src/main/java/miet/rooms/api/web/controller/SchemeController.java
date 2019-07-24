package miet.rooms.api.web.controller;

import miet.rooms.repository.dao.SchemeDao;
import miet.rooms.repository.entity.Scheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/scheme")
public class SchemeController {

    private final SchemeDao schemeDao;

    @Autowired
    public SchemeController(SchemeDao schemeDao) {
        this.schemeDao = schemeDao;
    }

    @GetMapping
    public Scheme getSchemeByName(@RequestParam Long floor, @RequestParam Long building) {
        return schemeDao.findAllByFloorAndBuilding(floor, building);
    }
}
