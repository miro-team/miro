package miet.rooms.repository.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import miet.rooms.repository.entity.RoomType;

@Repository
public interface RoomTypeDao extends JpaRepository<RoomType, Long> {
    RoomType findAllById(@Param("id") Long id);
}
