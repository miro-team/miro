package miet.rooms.api.service;

import miet.rooms.api.web.response.TeacherConfigResponse;
import miet.rooms.repository.jpa.dao.TeacherDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeacherService {

    private final TeacherDao teacherDao;

    @Autowired
    public TeacherService(TeacherDao teacherDao) {
        this.teacherDao = teacherDao;
    }

    public List<Object> findAllMapping() {
        return teacherDao.findAll().stream()
                .map(r -> TeacherConfigResponse.builder()
                        .id(r.getId())
                        .surname(r.getSurname())
                        .name(r.getName())
                        .patronymic(r.getPatronymic())
                        .fio(r.getFio())
                        .build()
                )
                .collect(Collectors.toList());
    }
}
