package miet.rooms.api.schedule.data.database.controller;

import miet.rooms.api.schedule.data.database.dao.AllDataDao;
import miet.rooms.api.schedule.data.database.dao.EngageTypeDao;
import miet.rooms.api.schedule.data.database.entity.AllData;
import miet.rooms.api.schedule.data.database.entity.EngageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/all-data")
public class AllDataController {

    @Autowired
    private AllDataDao allDataDao;

    @Autowired
    private EngageTypeDao engageTypeDao;

    @GetMapping("by-engage-type")
    public List<AllData> getAllByEngageType(@RequestParam Long engageTypeId) {
        EngageType engageType = engageTypeDao.findAllByEngageTypeId(engageTypeId);
        return allDataDao.findAllByEngageType(engageType);
    }
}
