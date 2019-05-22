package miet.rooms.api.schedule.controller;

import miet.rooms.api.schedule.data.database.dao.*;
import miet.rooms.api.schedule.data.database.entity.Pair;
import miet.rooms.api.schedule.data.database.entity.Room;
import miet.rooms.api.schedule.data.database.entity.RoomType;
import miet.rooms.api.schedule.data.database.entity.Scheme;
import miet.rooms.api.schedule.data.frontdata.FilteredData;
import miet.rooms.api.util.DateTimeHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/filter")
public class FilterController {

    @Autowired
    private GroupDao groupDao;

    @Autowired
    private RoomDao roomDao;

    @Autowired
    private PairDao pairDao;

    @Autowired
    private RoomTypeDao roomTypeDao;

    @Autowired
    private SchemeDao schemeDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping(value = "/data")
    public List<FilteredData> getFilteredData(@RequestParam(required = false) Long engagedById,
                                              @RequestParam(required = false) Long roomId,
                                              @RequestParam(required = false) Long weekType,
                                              @RequestParam(required = false) Long pairId,
                                              @RequestParam(required = false) Long weekNum,
                                              @RequestParam(required = false) @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate date,
                                              @RequestParam(required = false) Long building,
                                              @RequestParam(required = false) Long floor,
                                              @RequestParam(required = false) Long roomTypeId,
                                              @RequestParam(required = false) Long roomCapacity,
                                              @RequestParam(required = false) Long weekDay) {
        String queryStr = getQuery(engagedById, roomId, weekType, pairId, weekNum, weekDay);
        List<FilteredData> data = returnFilteredData(queryStr, building, floor, roomTypeId, roomCapacity, date);
        Collections.reverse(data);
        return data;

    }

    @GetMapping(value = "/count")
    public Integer getFilteredDataCount(@RequestParam(required = false) Long engagedById,
                                        @RequestParam(required = false) Long roomId,
                                        @RequestParam(required = false) Long weekType,
                                        @RequestParam(required = false) Long pairId,
                                        @RequestParam(required = false) Long weekNum,
                                        @RequestParam(required = false) @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate date,
                                        @RequestParam(required = false) Long building,
                                        @RequestParam(required = false) Long floor,
                                        @RequestParam(required = false) Long roomTypeId,
                                        @RequestParam(required = false) Long roomCapacity,
                                        @RequestParam(required = false) Long weekDay) {
        String queryStr = getQuery(engagedById, roomId, weekType, pairId, weekNum, weekDay);
        return returnFilteredData(queryStr, building, floor, roomTypeId, roomCapacity, date).size();
    }

    private List<FilteredData> returnFilteredData(String queryStr,
                                                  Long building,
                                                  Long floor,
                                                  Long roomTypeId,
                                                  Long roomCapacity,
                                                  LocalDate date) {
        List<FilteredData> dataList = jdbcTemplate.query(queryStr, (rs, rowNum) -> {
            FilteredData filteredData = new FilteredData();
            Long groupId = groupDao.findAllById(rs.getLong("engaged_by_id")).getId();
            filteredData.setEngagedById(groupId);

            filteredData.setDate(DateTimeHelper.dateToString(rs.getDate("date").toLocalDate()));

            Pair pair = pairDao.findAllById(rs.getLong("pair_id"));
            if (pair != null) {
                filteredData.setPairId(pair.getId());
            }

            Room room = roomDao.findAllById(rs.getLong("room_id"));
            if (room != null) {
                filteredData.setRoomId(room.getId());
            }

            filteredData.setWeekNum(rs.getLong("week_num"));
            filteredData.setWeekType(rs.getLong("week_type"));
            filteredData.setWeekDay(rs.getLong("week_day"));

            if (room != null) {
                filteredData.setCapacity(Long.valueOf(room.getCapacity()));

                RoomType roomType = room.getRoomType();
                if (roomType != null) {
                    filteredData.setRoomTypeId(roomType.getId());
                }

                Scheme scheme = room.getScheme();
                if (scheme != null) {
                    filteredData.setBuilding(scheme.getBuilding());
                    filteredData.setFloor(scheme.getFloor());
                }
            }
            return filteredData;
        });

        return dataList.stream()
                .filter(r -> {
                    if (roomCapacity != null) {
                        return roomDao.findAllById(r.getRoomId()).getCapacity().longValue() == roomCapacity;
                    }
                    return true;
                })
                .filter(r -> {
                    if (roomTypeId != null) {
                        return roomTypeDao.findAllById(roomTypeId) != null;
                    }
                    return true;
                })
                .filter(r -> {
                    if (building != null) {
                        return schemeDao.findAllByBuilding(building).size() != 0;
                    }
                    return true;
                })
                .filter(r -> {
                    if (floor != null) {
                        return schemeDao.findAllByFloor(floor).size() != 0;
                    }
                    return true;
                })
                .filter(r -> {
                    if(date != null) {
                        return r.getDate().equals(DateTimeHelper.dateToString(date));
                    }
                    return true;
                })
                .collect(Collectors.toList());
    }

    private String getQuery(Long engagedById,
                            Long roomId,
                            Long weekType,
                            Long pairId,
                            Long weekNum,
                            Long weekDay) {
        StringBuilder query = new StringBuilder("select * from ");
        query.append("schedule.all_data");
        if (engagedById != null ||
                roomId != null ||
                weekType != null ||
                pairId != null ||
                weekNum != null ||
                weekDay != null) {
            query.append(" where ");
        }
        if (engagedById != null) {
            query.append("engaged_by_id = ")
                    .append(engagedById)
                    .append("and ");
        }
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
        if (weekNum != null) {
            query.append("week_num = ")
                    .append(weekNum);

        }
        String queryStr = query.toString().trim().endsWith("and") ?
                query.toString().trim().substring(0, query.length() - 4) : query.toString();
        return queryStr + " order by date desc";
    }
}
