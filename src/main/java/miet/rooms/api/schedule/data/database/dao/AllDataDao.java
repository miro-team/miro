package miet.rooms.api.schedule.data.database.dao;

import miet.rooms.api.schedule.data.database.entity.AllData;
import miet.rooms.api.schedule.data.database.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Repository
public interface AllDataDao extends JpaRepository<AllData, Long> {
//    @Query(value = "select allData from AllData allData where allData.date=:date and allData.pair.id=:pair_id and allData.room.id=:room_id")
    List<Group> findGroupByDateAndAndPair_IdAndRoom_Id(@Param("date")LocalDate localDate, @Param("pair_id") Long pairId, @Param("room_id") Long roomId);
}
