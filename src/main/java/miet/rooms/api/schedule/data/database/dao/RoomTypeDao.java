package miet.rooms.api.schedule.data.database.dao;

import miet.rooms.api.schedule.data.database.entity.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomTypeDao extends JpaRepository<RoomType, Long> {
    RoomType findAllById(@Param("id") Long id);
}
