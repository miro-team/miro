package miet.rooms.api.service;

import io.swagger.models.auth.In;
import miet.rooms.repository.jpa.dao.PairDao;
import miet.rooms.repository.jpa.entity.Pair;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PairService {

    private final PairDao pairDao;

    @Autowired
    public PairService(PairDao pairDao) {
        this.pairDao = pairDao;
    }

    public Pair getPairById(@RequestParam Long id) {
        return pairDao.findAllById(id);
    }

    public Map<Long, Object> findAll() throws JSONException {
        Map<Long, Object> pairConfig = new HashMap<>();
        List<Pair> pairs = pairDao.findAll();
        for(Pair pair : pairs) {
            pairConfig.put(pair.getId(), pair.getName());
        }
        return pairConfig;
    }

    public Pair getPairByOrder(Integer order) {
        return pairDao.findAllByOrder(order);
    }
}
