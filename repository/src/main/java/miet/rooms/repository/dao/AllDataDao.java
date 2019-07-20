package miet.rooms.repository.dao;

import miet.rooms.repository.entity.AllData;
import miet.rooms.repository.entity.EngageType;
import miet.rooms.repository.entity.Group;
import miet.rooms.repository.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AllDataDao extends JpaRepository<AllData, Long> {
    List<Group> findGroupByDateAndAndPair_IdAndRoom_Id(@Param("date") LocalDate localDate, @Param("pair_id") Long pairId, @Param("room_id") Long roomId);

    List<Room> findRoomByDateAndAndPair_IdAndRoom_Id(@Param("date") LocalDate localDate, @Param("pair_id") Long pairId, @Param("room_id") Long roomId);

    AllData findAllById(@Param("id") Long id);

    List<AllData> findAllByDateAndAndPair_IdAndRoom_Id(@Param("date") LocalDate localDate, @Param("pair_id") Long pairId, @Param("room_id") Long roomId);

    void deleteById(@Param("id") Long id);

    List<AllData> findAllByEngageType(EngageType engageTypeId);
}
