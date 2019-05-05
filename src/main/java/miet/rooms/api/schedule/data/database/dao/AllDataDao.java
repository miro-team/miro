package miet.rooms.api.schedule.data.database.dao;

import miet.rooms.api.schedule.data.database.entity.AllData;
import miet.rooms.api.schedule.data.database.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AllDataDao extends JpaRepository<AllData, Long> {
    List<Group> findGroupByDateAndAndPair_IdAndRoom_Id(@Param("date")LocalDate localDate, @Param("pair_id") Long pairId, @Param("room_id") Long roomId);
}
