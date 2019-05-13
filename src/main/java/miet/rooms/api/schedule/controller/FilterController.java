package miet.rooms.api.schedule.controller;

import miet.rooms.api.schedule.data.database.dao.GroupDao;
import miet.rooms.api.schedule.data.database.dao.PairDao;
import miet.rooms.api.schedule.data.database.dao.RoomDao;
import miet.rooms.api.schedule.data.database.entity.Pair;
import miet.rooms.api.schedule.data.database.entity.Room;
import miet.rooms.api.schedule.data.frontdata.FilteredData;
import miet.rooms.api.util.DateTimeHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

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
    private JdbcTemplate jdbcTemplate;

    @GetMapping(value = "/data")
    public List<FilteredData> getFilteredData(@RequestParam(required = false) Long engagedById,
                                              @RequestParam(required = false) Long roomId,
                                              @RequestParam(required = false) Long weekType,
                                              @RequestParam(required = false) Long pairId,
                                              @RequestParam(required = false) Long weekNum
    ) {
        String queryStr = getQuery(engagedById, roomId, weekType, pairId, weekNum);
        List<FilteredData> data = returnFilteredData(queryStr);
        Collections.reverse(data);
        return data;

    }

    @GetMapping(value = "/count")
    public Integer getFilteredDataCount(@RequestParam(required = false) Long engagedById,
                                        @RequestParam(required = false) Long roomId,
                                        @RequestParam(required = false) Long weekType,
                                        @RequestParam(required = false) Long pairId,
                                        @RequestParam(required = false) Long weekNum) {
        String queryStr = getQuery(engagedById, roomId, weekType, pairId, weekNum);
        return returnFilteredData(queryStr).size();
    }

    private List<FilteredData> returnFilteredData(String queryStr) {
        return jdbcTemplate.query(queryStr, (rs, rowNum) -> {
            FilteredData filteredData = new FilteredData();
            String groupName = groupDao.findAllById(rs.getLong("engaged_by_id")).getName();
            filteredData.setEngagedBy(groupName);

            filteredData.setDate(DateTimeHelper.dateToString(rs.getDate("date").toLocalDate()));

            Pair pair  = pairDao.findAllById(rs.getLong("pair_id"));
            if(pair != null) {
                filteredData.setPairId(pair.getName());
            }

            Room room = roomDao.findAllById(rs.getLong("room_id"));
            if(room != null) {
                filteredData.setRoomId(room.getName());
            }

            filteredData.setWeekNum(rs.getInt("week_num"));
            filteredData.setWeekType(rs.getInt("week_type"));
            return filteredData;
        });
    }

    private String getQuery(Long engagedById,
                            Long roomId,
                            Long weekType,
                            Long pairId,
                            Long weekNum) {
        StringBuilder query = new StringBuilder("select * from ");
        query.append("schedule.all_data")
                .append(" where ");
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
        if (weekNum != null) {
            query.append("week_num = ")
                    .append(weekNum);

        }
        String queryStr = query.toString().trim().endsWith("and") ?
                query.toString().trim().substring(0, query.length() - 4) : query.toString();
        return queryStr + " order by date desc";
    }
}
