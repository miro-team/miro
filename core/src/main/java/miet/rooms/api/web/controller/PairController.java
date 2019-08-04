package miet.rooms.api.web.controller;

import miet.rooms.repository.jpa.dao.PairDao;
import miet.rooms.repository.jpa.entity.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/pair")
public class PairController {

    private final PairDao pairDao;

    @Autowired
    public PairController(PairDao pairDao) {
        this.pairDao = pairDao;
    }

    @GetMapping
    public Pair getPairByName(@RequestParam String pairName) {
        return pairDao.findAllByName(pairName);
    }
}
