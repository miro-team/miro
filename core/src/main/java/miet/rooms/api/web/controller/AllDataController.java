package miet.rooms.api.web.controller;

import miet.rooms.api.service.AllDataService;
import miet.rooms.repository.jpa.dao.AllDataDao;
import miet.rooms.repository.jpa.dao.EngageTypeDao;
import miet.rooms.repository.jpa.entity.AllData;
import miet.rooms.repository.jpa.entity.EngageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/all-data")
public class AllDataController {

    private final AllDataService allDataService;

    @Autowired
    public AllDataController(AllDataService allDataService) {
        this.allDataService = allDataService;
    }

    @GetMapping("by-engage-type")
    public List<AllData> getAllByEngageType(@RequestParam Long engageTypeId) {
        return allDataService.getAllByEngageTypeId(engageTypeId);
    }
}
