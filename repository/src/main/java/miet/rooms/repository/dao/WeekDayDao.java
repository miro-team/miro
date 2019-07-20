package miet.rooms.repository.dao;

import miet.rooms.repository.entity.WeekDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WeekDayDao extends JpaRepository<WeekDay, Long> {
    WeekDay findAllById(@Param("id") Long id);
}
