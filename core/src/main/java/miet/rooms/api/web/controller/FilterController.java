package miet.rooms.api.web.controller;

import miet.rooms.api.model.FilteredDataCycle;
import miet.rooms.api.model.FilteredDataSingle;
import miet.rooms.api.model.FilteredEventCycle;
import miet.rooms.api.model.FilteredEventSingle;
import miet.rooms.api.util.DateTimeHelper;
import lombok.extern.slf4j.Slf4j;
import miet.rooms.repository.dao.PairDao;
import miet.rooms.repository.dao.RoomDao;
import miet.rooms.repository.dao.WeekDayDao;
import miet.rooms.repository.entity.Pair;
import miet.rooms.repository.entity.Room;
import miet.rooms.repository.entity.Scheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@Slf4j
@RequestMapping("/filter")
public class FilterController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private RoomDao roomDao;

    @Autowired
    private PairDao pairDao;

    @Autowired
    private WeekDayDao weekDayDao;

//    @Autowired
//    private WeekTypeDao weekTypeDao;

    private Map<Long, Room> roomMap;

    @GetMapping(value = "/single")
    public FilteredDataSingle getFilteredSingleData(@RequestParam(required = false) Long roomId,
                                                   @RequestParam(required = false) Long weekType,
                                                   @RequestParam(required = false) Long pairId,
                                                   @RequestParam(required = false) Long weekNum,
                                                   @RequestParam String date,
                                                   @RequestParam(required = false) Long building,
                                                   @RequestParam(required = false) Long floor,
                                                   @RequestParam(required = false) Long roomTypeId,
                                                   @RequestParam(required = false) Long capacity,
                                                   @RequestParam(required = false) Long weekDay,
                                                   @RequestParam Long pageSize,
                                                   @RequestParam Long pageNum) {
        initMap();
        String queryStr = getQuerySingle(roomId, weekType, pairId, weekNum, date, building, floor, roomTypeId, capacity, weekDay, pageSize, pageNum);
        String queryCount = getQueryCountSingle(roomId, weekType, pairId, weekNum, date, building, floor, roomTypeId, capacity, weekDay);
        return getFilteredDataSingle(pageSize, pageNum, queryStr, queryCount);
    }

    @GetMapping(value = "/cycle")
    public FilteredDataCycle getFilteredCycleData(@RequestParam(required = false) Long roomId,
                                                  @RequestParam() Long weekType,
                                                  @RequestParam(required = false) Long pairId,
                                                  @RequestParam(required = false) Long building,
                                                  @RequestParam(required = false) Long floor,
                                                  @RequestParam(required = false) Long roomTypeId,
                                                  @RequestParam(required = false) Long capacity,
                                                  @RequestParam(required = false) Long weekDay,
                                                  @RequestParam Long pageSize,
                                                  @RequestParam Long pageNum) {
        initMap();
        String queryStr = getQueryCycle(roomId, weekType, pairId, building, floor, roomTypeId, capacity, weekDay, pageSize, pageNum);
        String queryCount = getQueryCountCycle(roomId, weekType, pairId, building, floor, roomTypeId, capacity, weekDay);
        return getFilteredDataCycle(pageSize, pageNum, queryStr, queryCount);
    }

    private void initMap() {
        if (roomMap == null) {
            roomMap = new HashMap<>();
            for (Room room : roomDao.findAll()) {
                roomMap.put(room.getId(), room);
            }
        }
    }

    private FilteredDataCycle getFilteredDataCycle(Long pageSize, Long pageNum, String queryStr, String queryCount) {
        List<FilteredEventCycle> data = returnFilteredEventsCycle(queryStr);
        Collections.reverse(data);
        FilteredDataCycle filteredDataCycle = new FilteredDataCycle();
        filteredDataCycle.setFilteredEventCycleList(data);
        filteredDataCycle.setPageNum(pageNum);
        filteredDataCycle.setPageSize(pageSize);
        filteredDataCycle.setTotalAmount(getCount(queryCount));
        return filteredDataCycle;
    }

    private FilteredDataSingle getFilteredDataSingle(Long pageSize, Long pageNum, String queryStr, String queryCount) {
        List<FilteredEventSingle> data = returnFilteredEventsSingle(queryStr);
        Collections.reverse(data);
        FilteredDataSingle filteredDataSingle = new FilteredDataSingle();
        filteredDataSingle.setFilteredEventSingleList(data);
        filteredDataSingle.setPageNum(pageNum);
        filteredDataSingle.setPageSize(pageSize);
        filteredDataSingle.setTotalAmount(getCount(queryCount));
        return filteredDataSingle;
    }

    private List<FilteredEventSingle> returnFilteredEventsSingle(String queryStr) {
        return jdbcTemplate.query(queryStr, (rs, rowNum) -> {
            FilteredEventSingle filteredEventSingle = new FilteredEventSingle();
            filteredEventSingle.setDate(DateTimeHelper.dateToString(rs.getDate("date").toLocalDate()));
            filteredEventSingle.setId(rs.getLong("id"));

            Long pairId = rs.getLong("pair_id");
            Pair pair = pairDao.findAllById(pairId);
            if(pair != null) {
                String pairStr = pair.getName() + " " + pair.getTimeFrom() + " " + pair.getTimeTo();
                filteredEventSingle.setPair(pairStr);
            }

            filteredEventSingle.setWeekDay(rs.getLong("week_day"));
            filteredEventSingle.setWeekNum(rs.getLong("week_num"));
//            filteredEventSingle.setWeekType(weekTypeDao.findWeekDayById(rs.getLong("week_type")));
            Long roomId = rs.getLong("room_id");
            Room room = roomMap.get(roomId);
            if (room != null) {
                filteredEventSingle.setRoom(room.getName());
                filteredEventSingle.setCapacity(room.getCapacity());
                filteredEventSingle.setRoomType(room.getRoomType().getName());
                Scheme scheme = room.getScheme();
                if (scheme != null) {
                    filteredEventSingle.setBuilding(scheme.getBuilding());
                    filteredEventSingle.setFloor(String.valueOf(scheme.getFloor()));
                }
            }
            return filteredEventSingle;
        });
    }

    private List<FilteredEventCycle> returnFilteredEventsCycle(String queryStr) {
        return jdbcTemplate.query(queryStr, (rs, rowNum) -> {
            FilteredEventCycle filteredEventCycle = new FilteredEventCycle();
            filteredEventCycle.setId(rs.getLong("id"));

            Long pairId = rs.getLong("pair_id");
            Pair pair = pairDao.findAllById(pairId);
            String pairStr = pair.getName() + " " + pair.getTimeFrom() + " " + pair.getTimeTo();
            filteredEventCycle.setPair(pairStr);
            filteredEventCycle.setWeekDay(weekDayDao.findWeekDayById(rs.getLong("week_day")));
            filteredEventCycle.setWeekType(weekDayDao.findWeekDayById(rs.getLong("week_type")));
            Long roomId = rs.getLong("room_id");
            Room room = roomMap.get(roomId);
            if (room != null) {
                filteredEventCycle.setRoom(room.getName());
                filteredEventCycle.setCapacity(room.getCapacity());
                filteredEventCycle.setRoomType(room.getRoomType().getName());
                Scheme scheme = room.getScheme();
                if (scheme != null) {
                    filteredEventCycle.setBuilding(scheme.getBuilding());
                    filteredEventCycle.setFloor(String.valueOf(scheme.getFloor()));
                }
            }
            return filteredEventCycle;
        });
    }

    private Long getCount(String queryCount) {
        return jdbcTemplate.queryForObject(
                queryCount, new Object[]{}, Long.class);
    }

    private String getQuerySingle(Long roomId,
                            Long weekType,
                            Long pairId,
                            Long weekNum,
                            String date,
                            Long building,
                            Long floor,
                            Long roomTypeId,
                            Long capacity,
                            Long weekDay,
                            Long pageSize,
                            Long pageNum) {
        StringBuilder query = new StringBuilder("select * from ");
        appendToQuerySingle(roomId, weekType, pairId, weekNum, date, building, floor, roomTypeId, capacity, weekDay, query);
        query.append(" order by date asc")
                .append(" limit ")
                .append(pageSize)
                .append(" offset ")
                .append((pageNum - 1) * pageSize);
        String queryStr = getQueryStrSingle(query);
        log.info(queryStr);
        return queryStr;
    }

    private String getQueryCycle(Long roomId,
                            Long weekType,
                            Long pairId,
                            Long building,
                            Long floor,
                            Long roomTypeId,
                            Long capacity,
                            Long weekDay,
                            Long pageSize,
                            Long pageNum) {
        StringBuilder query = new StringBuilder("select * from ");
        appendToQueryCycle(roomId, weekType, pairId, building, floor, roomTypeId, capacity, weekDay, query);
        query.append(" order by date asc")
                .append(" limit ")
                .append(pageSize)
                .append(" offset ")
                .append((pageNum - 1) * pageSize);
        String queryStr = getQueryStrCycle(query);
        log.info(queryStr);
        return queryStr;
    }

    private String getQueryCountCycle(Long roomId,
                                 Long weekType,
                                 Long pairId,
                                 Long building,
                                 Long floor,
                                 Long roomTypeId,
                                 Long capacity,
                                 Long weekDay) {
        StringBuilder query = new StringBuilder("select count(*) from ");
        appendToQueryCycle(roomId, weekType, pairId, building, floor, roomTypeId, capacity, weekDay, query);
        String queryStr = getQueryStrCycle(query);
        log.info(queryStr);
        return queryStr;
    }

    private String getQueryCountSingle(Long roomId,
                                 Long weekType,
                                 Long pairId,
                                 Long weekNum,
                                 String date,
                                 Long building,
                                 Long floor,
                                 Long roomTypeId,
                                 Long capacity,
                                 Long weekDay) {
        StringBuilder query = new StringBuilder("select count(*) from ");
        appendToQuerySingle(roomId, weekType, pairId, weekNum, date, building, floor, roomTypeId, capacity, weekDay, query);
        String queryStr = getQueryStrSingle(query);
        log.info(queryStr);
        return queryStr;
    }

    private String getQueryStrSingle(StringBuilder query) {
        String queryStr = query.toString().trim()
                .replaceAll(" +", " ")
                .replaceAll("and and", "and");
        return queryStr.replaceAll("and \\)", StringUtils.countOccurrencesOf(queryStr, "\\(") == 2 ? ")) " : ") ")
                .replaceAll("where and", "where");
    }

    private String getQueryStrCycle(StringBuilder query) {
        String queryStr = query.toString().trim()
                .replaceAll(" +", " ")
                .replaceAll("and and", "and");
        return queryStr.replaceAll("and \\)", StringUtils.countOccurrencesOf(queryStr, "\\(") == 2 ? ")) " : ") ")
                .replaceAll("where and", "where");
    }

    private void appendToQueryCycle(Long roomId, Long weekType, Long pairId, Long building, Long floor, Long roomTypeId, Long capacity, Long weekDay, StringBuilder query) {
        query.append("schedule.all_data");

        query.append(" where ");

        if (roomId != null) {
            query.append("room_id = ")
                    .append(roomId)
                    .append(" and ");
        }
        if (weekType != null) {
            query.append("week_type = ")
                    .append(weekType)
                    .append(" and ");
        }
        if (pairId != null) {
            query.append("pair_id = ")
                    .append(pairId)
                    .append(" and ");
        }
        if (weekDay != null) {
            query.append("week_day = ")
                    .append(weekDay)
                    .append(" and ");
        }

        if (roomTypeId != null || capacity != null || building != null || floor != null) {
            query.append("room_id in (select id from locations.rooms where ");

            if (roomTypeId != null) {
                query.append("room_type_id = ")
                        .append(roomTypeId)
                        .append(" and ");
            }
            if (capacity != null) {
                query.append("capacity = ")
                        .append(capacity)
                        .append(" and ");
            }

            if (building != null || floor != null) {
                query.append("scheme_id in (select id from locations.schemes where ");

                if (building != null) {
                    query.append("building = ")
                            .append(building)
                            .append(" and ");
                }
                if (floor != null) {
                    query.append("floor = ")
                            .append(floor);
                }
                query.append(")) and ");

            } else {
                query.append(") ");
            }
        }
        query.append(" and engaged_by_id is null");
    }

    private void appendToQuerySingle(Long roomId, Long weekType, Long pairId, Long weekNum, String date, Long building, Long floor, Long roomTypeId, Long capacity, Long weekDay, StringBuilder query) {
        query.append("schedule.all_data");

        query.append(" where ");

        if (roomId != null) {
            query.append("room_id = ")
                    .append(roomId)
                    .append(" and ");
        }
        if (weekType != null) {
            query.append("week_type = ")
                    .append(weekType)
                    .append(" and ");
        }
        if (pairId != null) {
            query.append("pair_id = ")
                    .append(pairId)
                    .append(" and ");
        }
        if (weekNum != null) {
            query.append("week_num = ")
                    .append(weekNum)
                    .append(" and ");
        }
        if (weekDay != null) {
            query.append("week_day = ")
                    .append(weekDay)
                    .append(" and ");
        }
        if (date != null) {
            query.append("date = to_date('")
                    .append(date)
                    .append("', 'dd.MM.yyyy')")
                    .append(" and ");
        }

        if (roomTypeId != null || capacity != null || building != null || floor != null) {
            query.append("room_id in (select id from locations.rooms where ");

            if (roomTypeId != null) {
                query.append("room_type_id = ")
                        .append(roomTypeId)
                        .append(" and ");
            }
            if (capacity != null) {
                query.append("capacity = ")
                        .append(capacity)
                        .append(" and ");
            }

            if (building != null || floor != null) {
                query.append("scheme_id in (select id from locations.schemes where ");

                if (building != null) {
                    query.append("building = ")
                            .append(building)
                            .append(" and ");
                }
                if (floor != null) {
                    query.append("floor = ")
                            .append(floor);
                }
                query.append(")) and ");

            } else {
                query.append(") ");
            }
        }
        query.append(" and engaged_by_id is null");
    }
}
