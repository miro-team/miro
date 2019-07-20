package miet.rooms.api.web.controller;

import miet.rooms.repository.dao.AllDataDao;
import miet.rooms.repository.dao.EngageTypeDao;
import miet.rooms.repository.entity.AllData;
import miet.rooms.repository.entity.EngageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/all-data")
//@ComponentScan(basePackages = {"miet.rooms.repository.dao"})
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
