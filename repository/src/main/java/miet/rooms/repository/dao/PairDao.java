package miet.rooms.repository.dao;

import miet.rooms.repository.entity.Pair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PairDao extends JpaRepository<Pair, Long> {
    Pair findAllByName(@Param("name") String name);

    Pair findAllById(@Param("id") Long id);
}
