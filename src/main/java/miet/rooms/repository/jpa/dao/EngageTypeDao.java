package miet.rooms.repository.jpa.dao;

import miet.rooms.repository.jpa.entity.EngageType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EngageTypeDao extends JpaRepository<EngageType, Long> {
    EngageType findAllById(@Param("id") Long id);
}
