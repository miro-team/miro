package miet.rooms.api.service;

import miet.rooms.api.web.response.PairConfigResponse;
import miet.rooms.repository.jpa.dao.PairDao;
import miet.rooms.repository.jpa.entity.Pair;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<Object> findAllMapping() throws JSONException {
        return pairDao.findAll().stream()
                .map(r -> PairConfigResponse.builder()
                        .id(r.getId())
                        .name(r.getName())
                        .timeFrom(r.getTimeFrom())
                        .timeTo(r.getTimeTo())
                        .build()
                )
                .collect(Collectors.toList());
    }

    public Pair getPairByOrder(Integer order) {
        return pairDao.findAllByOrder(order);
    }
}
