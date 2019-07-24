package miet.rooms.api.web.controller;

import miet.rooms.api.model.FilteredData;
import miet.rooms.api.model.FilteredEvent;
import miet.rooms.api.util.DateTimeHelper;
import lombok.extern.slf4j.Slf4j;
import miet.rooms.repository.dao.RoomDao;
import miet.rooms.repository.entity.Room;
import miet.rooms.repository.entity.Scheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

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
    private RoomDao roomDao;

    private Map<Long, Room> roomMap;

    @GetMapping(value = "/data")
    public FilteredData getFilteredData(@RequestParam(required = false) Long roomId,
                                        @RequestParam(required = false) Long weekType,
                                        @RequestParam(required = false) Long pairId,
                                        @RequestParam(required = false) Long weekNum,
                                        @RequestParam(required = false) String date,
                                        @RequestParam(required = false) Long building,
                                        @RequestParam(required = false) Long floor,
                                        @RequestParam(required = false) Long roomTypeId,
                                        @RequestParam(required = false) Long capacity,
                                        @RequestParam(required = false) Long weekDay,
                                        @RequestParam Long pageSize,
                                        @RequestParam Long pageNum) {
        initMap();
        String queryStr = getQuery(roomId, weekType, pairId, weekNum, date, building, floor, roomTypeId, capacity, weekDay, pageSize, pageNum);
        String queryCount = getQueryCount(roomId, weekType, pairId, weekNum, date, building, floor, roomTypeId, capacity, weekDay);
        return getFilteredData(pageSize, pageNum, queryStr, queryCount);
    }

    private void initMap() {
        if (roomMap == null) {
            roomMap = new HashMap<>();
            for (Room room : roomDao.findAll()) {
                roomMap.put(room.getId(), room);
            }
        }
    }

    private FilteredData getFilteredData(Long pageSize, Long pageNum, String queryStr, String queryCount) {
        List<FilteredEvent> data = returnFilteredEvents(queryStr);
        Collections.reverse(data);
        FilteredData filteredData = new FilteredData();
        filteredData.setFilteredEventList(data);
        filteredData.setPageNum(pageNum);
        filteredData.setPageSize(pageSize);
        filteredData.setTotalAmount(getCount(queryCount));
        return filteredData;
    }

    private List<FilteredEvent> returnFilteredEvents(String queryStr) {
        return jdbcTemplate.query(queryStr, (rs, rowNum) -> {
            FilteredEvent filteredEvent = new FilteredEvent();
            filteredEvent.setDate(DateTimeHelper.dateToString(rs.getDate("date").toLocalDate()));
            filteredEvent.setEngageTypeId(rs.getLong("engage_type_id"));
            filteredEvent.setGroupId(rs.getLong("engaged_by_id"));
            filteredEvent.setId(rs.getLong("id"));
            filteredEvent.setPairId(rs.getLong("pair_id"));
            filteredEvent.setWeekDay(rs.getLong("week_day"));
            filteredEvent.setWeekNum(rs.getLong("week_num"));
            filteredEvent.setWeekType(rs.getLong("week_type"));
            Long roomId = rs.getLong("room_id");
            Room room = roomMap.get(roomId);
            if (room != null) {
                filteredEvent.setRoomId(roomId);
                filteredEvent.setCapacity(room.getCapacity());
                filteredEvent.setRoomTypeId(room.getRoomType().getId());
                Scheme scheme = room.getScheme();
                if (scheme != null) {
                    filteredEvent.setBuilding(scheme.getBuilding());
                    filteredEvent.setFloor(scheme.getFloor());
                }
            }
            return filteredEvent;
        });
    }

    private Long getCount(String queryCount) {
        return jdbcTemplate.queryForObject(
                queryCount, new Object[]{}, Long.class);
    }

    private String getQuery(Long roomId,
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
        appendToQuery(roomId, weekType, pairId, weekNum, date, building, floor, roomTypeId, capacity, weekDay, query);
        query.append(" order by date asc")
                .append(" limit ")
                .append(pageSize)
                .append(" offset ")
                .append((pageNum - 1) * pageSize);
        String queryStr = getQueryStr(query);
        log.info(queryStr);
        return queryStr;
    }

    private String getQueryCount(Long roomId,
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
        appendToQuery(roomId, weekType, pairId, weekNum, date, building, floor, roomTypeId, capacity, weekDay, query);
        String queryStr = getQueryStr(query);
        log.info(queryStr);
        return queryStr;
    }

    private String getQueryStr(StringBuilder query) {
        String queryStr = query.toString().trim()
                .replaceAll(" +", " ")
                .replaceAll("and and", "and");
        return queryStr.replaceAll("and \\)", StringUtils.countOccurrencesOf(queryStr, "\\(") == 2 ? ")) " : ") ")
                .replaceAll("where and", "where");
    }

    private void appendToQuery(Long roomId, Long weekType, Long pairId, Long weekNum, String date, Long building, Long floor, Long roomTypeId, Long capacity, Long weekDay, StringBuilder query) {
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
