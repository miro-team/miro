package miet.rooms.api.web.controller;

import miet.rooms.api.model.Event;
import miet.rooms.api.model.TransferEvent;
import miet.rooms.api.util.DateTimeHelper;
import miet.rooms.repository.dao.*;
import miet.rooms.repository.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/event")
public class EventController {

    @Autowired
    private RoomDao roomDao;

    @Autowired
    private AllDataDao allDataDao;

    @Autowired
    private PairDao pairDao;

    @Autowired
    private GroupDao groupDao;

    @Autowired
    private EngageTypeDao engageTypeDao;

    @Autowired
    private CycleEventDao cycleEventDao;

    @Autowired
    private EngageDao engageDao;

    @Autowired
    private CycleDao cycleDao;

    @Autowired
    private TransferDao transferDao;

    @PostMapping(value = "/simple")
    public void addSimpleEvent(@RequestBody Event event) {
        if (checkRoomFree(event)) {
            saveAllData(event);

            saveEngage(event);
        }
    }

    @DeleteMapping(value = "/simple")
    @Transactional
    public void deleteSimpleEvent(@RequestParam(name = "all_data_id") Long allDataId) {
        engageDao.deleteByAllData_Id(allDataId);
        allDataDao.deleteById(allDataId);
    }

    @PostMapping(value = "/cycle")
    public void addCycleEvent(@RequestBody Event event) {
        int cycleLength = cycleEventDao.findAll().size();
        //TODO: improve cycle. We don't always need 5 cycles
        long seqNum = 0;
        String cycleId = DateTimeHelper.dateToId(LocalDateTime.now());
        for (int i = 0; i < 5; i++) {
            if (checkRoomFree(event)) {
                LocalDate date = DateTimeHelper.asLocalDate(event.getDate()).plusWeeks(i * cycleLength);
                AllData allData = fillData(event, date);
                allDataDao.save(allData);

                Cycle cycle = new Cycle();
                cycle.setAllData(allData);
                cycle.setSeqNum(seqNum++);
                cycle.setCycleId(cycleId);
                cycleDao.save(cycle);
            }
        }
    }

    @DeleteMapping(value = "/cycle")
    @Transactional
    public void deleteCycleEvent(@RequestParam(name = "all_data_id") Long allDataId) {
        Cycle cycle = cycleDao.findAllByAllData_Id(allDataId);
        List<AllData> datas = cycleDao.findAllDataByCycleId(cycle.getCycleId());
        cycleDao.deleteByCycleId(cycle.getCycleId());
        allDataDao.deleteAll(datas);
    }

    @PostMapping(value = "/transfer")
    public void addTransferEvent(@RequestBody TransferEvent event) {
        //TODO: защита если уже перенесен
        AllData fromAllData = allDataDao.findAllById(event.getFromAllDataId());
        if (checkRoomFree(event)) {
            AllData toAllData;
            toAllData = fillData(event);
            allDataDao.save(toAllData);

            Transfer transfer = new Transfer();
            transfer.setFromAllData(fromAllData);
            transfer.setToAllData(toAllData);

            String cycleId = transferDao.findCycleIdByFromAllData_Id(event.getFromAllDataId());
            if(cycleId == null) {
                cycleId = DateTimeHelper.dateToId(LocalDateTime.now());
            }
            transfer.setCycleId(cycleId);

            Long seqNum = transferDao.findSeqNumByFromAllData_IdAndCycleId(fromAllData.getId(), cycleId);
            if(seqNum == null) {
                seqNum = -1L;
            }
            transfer.setSeqNum(++seqNum);
        }
    }

    @DeleteMapping(value = "/transfer/all")
    @Transactional
    public void deleteTransferAllEvent(@RequestParam(name = "from_all_data_id") Long fromAllDataId) {
        Transfer transfer = transferDao.findAllByFromAllData_Id(fromAllDataId);
        List<AllData> datas = transferDao.findAllDataByCycleId(transfer.getCycleId());
        transferDao.deleteByCycleId(transfer.getCycleId());
        allDataDao.deleteAll(datas);
    }

    @DeleteMapping(value = "/transfer/from-current")
    @Transactional
    public void deleteTransferFromCurrentEvent(@RequestParam(name = "from_all_data_id") Long fromAllDataId) {
        Transfer transfer = transferDao.findAllByFromAllData_Id(fromAllDataId);
        Long seqNum = transferDao.findSeqNumByFromAllData_IdAndCycleId(fromAllDataId, transfer.getCycleId());
        List<AllData> datas = transferDao.findAllDataByCycleIdAndSeqNum(transfer.getCycleId(), seqNum);
        transferDao.deleteByFromAllData(datas);
        allDataDao.deleteAll(datas);
    }

    private void saveEngage(@RequestBody Event event) {
        Engage engage = fillEngage(event);
        engageDao.save(engage);
    }

    private void saveAllData(@RequestBody Event event) {
        AllData allData;
        LocalDate date = DateTimeHelper.asLocalDate(event.getDate());
        allData = fillData(event, date);
        allDataDao.save(allData);
    }

    private Engage fillEngage(Event event) {
        Engage engage = new Engage();

        Group group = groupDao.findAllByName(event.getEngagedBy());
        if (group == null) {
            group = new Group();
            group.setName(event.getEngagedBy());
            groupDao.save(group);
        }
        engage.setEngagedBy(group);

        EngageType engageType = engageTypeDao.findAllByEngageTypeId(event.getEngageTypeId());
        engage.setEngagedType(engageType);

        if (event.getFromAllDataId() != null) {
            AllData allData = allDataDao.findAllById(event.getFromAllDataId());
            engage.setTransferredFrom(allData);
        }

        LocalDate date = DateTimeHelper.asLocalDate(event.getDate());
        AllData originData = allDataDao.findAllByDateAndAndPair_IdAndRoom_Id(date, event.getPairId(), event.getRoomId()).get(0);
        engage.setAllData(originData);

        engage.setInsertDate(LocalDateTime.now());

        String teacherName = event.getTeacherName() != null ? event.getTeacherName() : "";
        engage.setTeacherName(teacherName);

        return engage;
    }

    private AllData fillData(@RequestBody Event event, LocalDate date) {
        AllData allData = new AllData();
        allData.setDate(date);

        Pair pair = pairDao.findAllById(event.getPairId());
        allData.setPair(pair);

        Room room = roomDao.findAllById(event.getRoomId());
        allData.setRoom(room);

        Group group = groupDao.findAllByName(event.getEngagedBy());
        if (group == null) {
            group = new Group();
            group.setName(event.getEngagedBy());
            groupDao.save(group);
        }
        allData.setGroup(group);

        allData.setWeekType(event.getWeekType());

        EngageType engageType = engageTypeDao.findAllByEngageTypeId(event.getEngageTypeId());
        allData.setEngageType(engageType);

        return allData;
    }

    private AllData fillData(@RequestBody TransferEvent event) {
        AllData allData = new AllData();
        AllData fromAllData = allDataDao.findAllById(event.getFromAllDataId());
        allData.setDate(fromAllData.getDate());

        allData.setPair(fromAllData.getPair());

        Room room = roomDao.findAllById(event.getRoomId());
        allData.setRoom(room);

        allData.setGroup(fromAllData.getGroup());

        allData.setWeekType(fromAllData.getWeekType());

        return allData;
    }

    private boolean checkRoomFree(Event event) {
        LocalDate date = DateTimeHelper.asLocalDate(event.getDate());
        Room room = allDataDao.findRoomByDateAndAndPair_IdAndRoom_Id(date, event.getPairId(), event.getRoomId()).get(0);
        return room == null;
    }

    private boolean checkRoomFree(TransferEvent event) {
        AllData fromAllData = allDataDao.findAllById(event.getFromAllDataId());
        LocalDate date = fromAllData.getDate();
        Room room = allDataDao.findRoomByDateAndAndPair_IdAndRoom_Id(date, fromAllData.getPair().getId(), event.getRoomId()).get(0);
        return room == null;
    }
}
