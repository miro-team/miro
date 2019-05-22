package miet.rooms.api.schedule.data.database.controller;

import miet.rooms.api.schedule.data.database.dao.SchemeDao;
import miet.rooms.api.schedule.data.database.entity.Scheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/scheme")
public class SchemeController {

    @Autowired
    private SchemeDao schemeDao;

    @GetMapping
    public Scheme getSchemeByName(@RequestParam Long floor, @RequestParam Long building) {
        return schemeDao.findAllByFloorAndBuilding(floor, building);
    }
}
