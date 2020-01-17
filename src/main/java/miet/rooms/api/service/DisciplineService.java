package miet.rooms.api.service;

import miet.rooms.api.web.response.DisciplineConfigResponse;
import miet.rooms.repository.jpa.dao.DisciplineDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DisciplineService {

    private final DisciplineDao disciplineDao;

    @Autowired
    public DisciplineService(DisciplineDao disciplineDao) {
        this.disciplineDao = disciplineDao;
    }

    public List<Object> findAllMapping() {
        return disciplineDao.findAll().stream()
                .map(r -> DisciplineConfigResponse.builder()
                        .id(r.getId())
                        .disTypeId(r.getDisType().getId())
                        .title(r.getTitle())
                        .build()
                )
                .collect(Collectors.toList());

    }
}
