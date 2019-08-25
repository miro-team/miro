package miet.rooms.repository.jpa.dao;

import miet.rooms.repository.jpa.entity.EventType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EventTypeDao extends JpaRepository<EventType, Long> {
    EventType findAllById(@Param("id") Long id);
}
