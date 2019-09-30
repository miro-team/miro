package miet.rooms.repository.jpa.dao;

import miet.rooms.repository.jpa.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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

    AllData findFirstByDate(@Param("date") LocalDate localDate);

    AllData findFirstByOrderByDateAsc();

    AllData findFirstByOrderByWeekNumDesc();

    void deleteByWeekNum(@Param("week_num") Long weekNum);

    List<AllData> findAllByEngageType_Id(@Param("engage_type_id") Long engageTypeId);

    @Query(value = "select allData from AllData allData where allData.id in (:ids)")
    List<AllData> findAllById(@Param("ids") Long[] ids);

    List<AllData> findAllIdByReservation_Id(@Param("reservation_id") Long reservationId);

    void deleteAllByReservation_Id(@Param("reservation_id") Long reservationId);

    @Query("from AllData allData where allData.reservation.id in (:reservation_id)")
    List<AllData> findAllByReservation_Id(@Param("reservation_id") List<Long> reservationsId);
}
