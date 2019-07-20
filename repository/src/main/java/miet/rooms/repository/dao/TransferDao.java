package miet.rooms.repository.dao;

import miet.rooms.repository.entity.AllData;
import miet.rooms.repository.entity.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransferDao extends JpaRepository<Transfer, Long> {
    void deleteByCycleId(@Param("cycle_id") String cycleId);

    void deleteByFromAllData(@Param("from_all_data") List<AllData> allDatas);

    Transfer findAllByFromAllData_Id(@Param("from_all_data_id") Long allDataId);

    @Query(value = "select transfer.fromAllData from Transfer transfer where transfer.cycleId=:cycle_id")
    List<AllData> findAllDataByCycleId(@Param("cycle_id") String cycleId);

    @Query(value = "select transfer.fromAllData from Transfer transfer where transfer.cycleId=:cycle_id and transfer.seqNum >=:seq_num")
    List<AllData> findAllDataByCycleIdAndSeqNum(@Param("cycle_id") String cycleId, @Param("seq_num") Long seqNum);

    @Query(value = "select transfer.seqNum from Transfer transfer where transfer.fromAllData.id=:from_all_data_id " +
            "and transfer.cycleId=:cycle_id")
    Long findSeqNumByFromAllData_IdAndCycleId(@Param("from_all_data_id") Long fromAllDataId, @Param("cycle_id") String cycleId);

    @Query(value = "select transfer.seqNum from Transfer transfer where transfer.fromAllData.id=:from_all_data_id")
    String findCycleIdByFromAllData_Id(@Param("from_all_data_id") Long fromAllDataId);
}
