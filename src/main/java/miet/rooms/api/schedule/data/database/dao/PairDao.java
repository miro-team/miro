package miet.rooms.api.schedule.data.database.dao;

import miet.rooms.api.schedule.data.database.entity.Pair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PairDao extends JpaRepository<Pair, Long> {
    Pair findAllByName(@Param("name") String name);

    Pair findAllById(@Param("id") Long id);
}
