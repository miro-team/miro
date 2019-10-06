package miet.rooms.repository.jpa.dao;

import miet.rooms.repository.jpa.entity.WeekDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

@Repository
public interface WeekDayDao extends JpaRepository<WeekDay, Long> {
    WeekDay findAllById(@Param("id") Long id);

//    WeekDay findAllByOrder(@Param("order") Integer order);
}
