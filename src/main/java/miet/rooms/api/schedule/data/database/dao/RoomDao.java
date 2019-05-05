package miet.rooms.api.schedule.data.database.dao;

import miet.rooms.api.schedule.data.database.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomDao extends JpaRepository<Room, Long> {
    Room findAllByName(@Param("name") String name);
}
