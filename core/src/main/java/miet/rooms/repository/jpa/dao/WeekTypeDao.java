package miet.rooms.repository.jpa.dao;

import miet.rooms.repository.jpa.entity.WeekType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WeekTypeDao extends JpaRepository<WeekType, Long> {
    WeekType findAllByWeekTypeCodeNum(@Param("week_type_code_num") Long weekTypeCodeNum);
}
