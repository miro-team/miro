package miet.rooms.repository.dao;

import miet.rooms.repository.entity.EngageType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface EngageTypeDao extends JpaRepository<EngageType, Long> {
    EngageType findAllByEngageTypeId(@Param("engage_type_id") Long engageTypeId);

    EngageType findAllByDescription(@Param("description") String description);
}
