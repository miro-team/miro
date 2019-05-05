package miet.rooms.api.schedule.data.database.dao;

import miet.rooms.api.schedule.data.database.entity.CycleEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CycleEventDao extends JpaRepository<CycleEvent, Long> {
}
