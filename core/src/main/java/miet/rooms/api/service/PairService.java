package miet.rooms.api.service;

import miet.rooms.repository.jpa.dao.PairDao;
import miet.rooms.repository.jpa.entity.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class PairService {

    private final PairDao pairDao;

    @Autowired
    public PairService(PairDao pairDao) {
        this.pairDao = pairDao;
    }

    public Pair getPairByName(@RequestParam Long id) {
        return pairDao.findAllById(id);
    }
}
