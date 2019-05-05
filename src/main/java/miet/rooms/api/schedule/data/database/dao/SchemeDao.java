package miet.rooms.api.schedule.data.database.dao;

import miet.rooms.api.schedule.data.database.entity.Scheme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchemeDao extends JpaRepository<Scheme, Long> {
}
