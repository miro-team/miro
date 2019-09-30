package miet.rooms.repository.jpa.dao;

import miet.rooms.repository.jpa.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupDao extends JpaRepository<Group, Long> {
    Group findAllByName(@Param("name") String name);

    Group findAllById(@Param("id") Long id);
}
