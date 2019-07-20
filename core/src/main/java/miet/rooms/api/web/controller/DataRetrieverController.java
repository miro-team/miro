package miet.rooms.api.web.controller;

import lombok.extern.slf4j.Slf4j;
import miet.rooms.api.model.AllDataFront;
import miet.rooms.api.model.Retriever;
import miet.rooms.api.util.DateTimeHelper;
import miet.rooms.repository.dao.AllDataDao;
import miet.rooms.repository.dao.CycleDao;
import miet.rooms.repository.dao.EngageDao;
import miet.rooms.repository.dao.EngageTypeDao;
import miet.rooms.repository.dao.GroupDao;
import miet.rooms.repository.dao.PairDao;
import miet.rooms.repository.dao.RoomDao;
import miet.rooms.repository.dao.RoomTypeDao;
import miet.rooms.repository.dao.SchemeDao;
import miet.rooms.repository.dao.TransferDao;
import miet.rooms.repository.entity.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/retriever")
@Slf4j
public class DataRetrieverController {

    @Autowired
    private PairDao pairDao;

    @Autowired
    private AllDataDao allDataDao;

    @Autowired
    private GroupDao groupDao;

    @Autowired
    private RoomDao roomDao;

    @Autowired
    private RoomTypeDao roomTypeDao;

    @Autowired
    private SchemeDao schemeDao;

    @Autowired
    private EngageTypeDao engageTypeDao;

    @Autowired
    private TransferDao transferDao;

    @Autowired
    private CycleDao cycleDao;

    @Autowired
    private EngageDao engageDao;

    @Autowired
    private Retriever retriever;

    @PostMapping("/get")
    public Retriever returnAll() {
        return retriever;
    }

    @GetMapping("/initialize")
    public void initialize() {
        List<AllDataFront> allDataFronts = allDataDao.findAll().stream()
                                                     .map(r -> {
                    AllDataFront allDataFront = new AllDataFront();
                    allDataFront.setDate(DateTimeHelper.dateToString(r.getDate()));
                    allDataFront.setEngageTypeId(r.getEngageType().getEngageTypeId());
                    Group group = r.getGroup();
                    allDataFront.setGroupId(group != null ? group.getId() : -1L);
                    allDataFront.setId(r.getId());
                    allDataFront.setPairId(r.getPair().getId());
                    allDataFront.setRoomId(r.getRoom().getId());
                    allDataFront.setWeekDay(r.getWeekDay());
                    allDataFront.setWeekNum(r.getWeekNum());
                    allDataFront.setWeekType(r.getWeekType());
                    return allDataFront;
                })
                                                     .collect(Collectors.toList());
        retriever.setAllDataList(allDataFronts);
        retriever.setRooms(roomDao.findAll());
        retriever.setSchemes(schemeDao.findAll());
        retriever.setGroups(groupDao.findAll());
        retriever.setRoomTypes(roomTypeDao.findAll());
        retriever.setPairs(pairDao.findAll());
//      retriever.setTransfers(transferDao.findAll());
//      retriever.setCycles(cycleDao.findAll());
//      retriever.setEngages(engageDao.findAll());
        retriever.setEngageTypes(engageTypeDao.findAll());
    }
}
