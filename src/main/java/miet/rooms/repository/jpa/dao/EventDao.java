package miet.rooms.repository.jpa.dao;

import miet.rooms.repository.jpa.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EventDao extends JpaRepository<Event, Long> {
    Event findAllById(@Param("id") Long id);

    void deleteById(@Param("id") Long id);

    Event findFirstByDate(@Param("date") LocalDate localDate);

    Event findFirstByOrderByDateAsc();

    Event findFirstByOrderByWeekNumDesc();

    void deleteByWeekNum(@Param("week_num") Long weekNum);

//    List<Event> findAllByEngageType_Id(@Param("engage_type_id") Long engageTypeId);

    @Query(value = "select event from Event event where event.id in (:ids)")
    List<Event> findAllById(@Param("ids") Long[] ids);

    @Query("update Event event set event.engId = null")
    @Modifying
    @Transactional
    void clearEngId();

    @Query("update Event event set event.engId = null where event.engId=:engId")
    @Modifying
    @Transactional
    void clearEngId(@Param("engId") Long engId);

    @Query("update Event event set event.engId = :engId where event.date=:date and event.room.id=:roomId and event.pair.id=:pairId")
    @Modifying
    @Transactional
    void update(@Param("engId") Long engId, @Param("date") LocalDate date, @Param("roomId") Long roomId, @Param("pairId") Long pairId);

    Event findFirstByEngId(Long engId);

    List<Event> findAllByEngId(Long engId);
}
