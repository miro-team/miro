package miet.rooms.repository.jpa.dao;

import miet.rooms.repository.jpa.entity.Pair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PairDao extends JpaRepository<Pair, Long> {
    Pair findAllByName(@Param("name") String name);

    Pair findAllById(@Param("id") Long id);

    Pair findAllByOrder(@Param("order") Integer order);
}
