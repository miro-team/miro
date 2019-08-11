package miet.rooms.api.web.controller;

import miet.rooms.api.service.PairService;
import miet.rooms.repository.jpa.dao.PairDao;
import miet.rooms.repository.jpa.entity.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pair")
public class PairController {

    private final PairService pairService;

    @Autowired
    public PairController(PairService pairService) {
        this.pairService = pairService;
    }

    @GetMapping("by-id")
    public Pair getPairByName(@RequestParam Long id) {
        return pairService.getPairByName(id);
    }
}
