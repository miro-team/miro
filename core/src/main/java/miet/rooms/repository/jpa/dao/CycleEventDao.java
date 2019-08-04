package miet.rooms.repository.jpa.dao;

import miet.rooms.repository.jpa.entity.CycleEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CycleEventDao extends JpaRepository<CycleEvent, Long> {
    CycleEvent findAllById(@Param("id") Long id);
}
