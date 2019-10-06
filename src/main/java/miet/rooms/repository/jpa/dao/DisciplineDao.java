package miet.rooms.repository.jpa.dao;

import miet.rooms.repository.jpa.entity.Discipline;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DisciplineDao extends JpaRepository<Discipline, Long> {
}
