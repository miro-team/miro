package miet.rooms.repository.jpa.dao;

import miet.rooms.repository.jpa.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherDao extends JpaRepository<Teacher, Long> {
}
