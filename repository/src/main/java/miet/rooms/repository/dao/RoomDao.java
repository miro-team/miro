package miet.rooms.repository.dao;

import miet.rooms.repository.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomDao extends JpaRepository<Room, Long> {
    Room findAllByName(@Param("name") String name);

    Room findAllById(@Param("id") Long id);
}
