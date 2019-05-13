package miet.rooms.api.schedule.data.database.dao;

import miet.rooms.api.schedule.data.database.entity.AllData;
import miet.rooms.api.schedule.data.database.entity.Cycle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CycleDao extends JpaRepository<Cycle, Long> {
    void deleteByCycleId(@Param("cycle_id") String cycleId);

    Cycle findAllByAllData_Id(@Param("all_data_id") Long allDataId);

    @Query(value = "select cycle.allData from Cycle cycle where cycle.cycleId=:cycle_id")
    List<AllData> findAllDataByCycleId(@Param("cycle_id") String cycleId);
}
