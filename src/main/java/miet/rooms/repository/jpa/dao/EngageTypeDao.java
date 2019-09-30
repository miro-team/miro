package miet.rooms.repository.jpa.dao;

import miet.rooms.repository.jpa.entity.EngageType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface EngageTypeDao extends JpaRepository<EngageType, Long> {
    EngageType findAllByEngageTypeId(@Param("engage_type_id") Long engageTypeId);

    EngageType findAllByDescription(@Param("description") String description);
}
