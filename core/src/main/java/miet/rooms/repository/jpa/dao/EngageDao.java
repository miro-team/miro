package miet.rooms.repository.jpa.dao;

import miet.rooms.repository.jpa.entity.Engage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EngageDao extends JpaRepository<Engage, Long> {
    void deleteByAllData_Id(@Param("all_data_id") Long allDataId);
}
