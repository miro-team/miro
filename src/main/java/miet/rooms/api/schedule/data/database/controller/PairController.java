package miet.rooms.api.schedule.data.database.controller;

import miet.rooms.api.schedule.data.database.dao.PairDao;
import miet.rooms.api.schedule.data.database.entity.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/pair")
public class PairController {

    @Autowired
    private PairDao pairDao;

    @GetMapping
    public Pair getPairByName(@RequestParam String pairName) {
        return pairDao.findAllByName(pairName);
    }
}
