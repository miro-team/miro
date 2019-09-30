package miet.rooms.repository.jpa.dao;

import miet.rooms.repository.jpa.entity.Periodicity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PeriodicityDao extends JpaRepository<Periodicity, Long> {
    Periodicity findAllById(@Param("id") Long id);
}
