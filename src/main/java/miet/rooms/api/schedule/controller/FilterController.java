package miet.rooms.api.schedule.controller;

import lombok.extern.slf4j.Slf4j;
import miet.rooms.api.schedule.data.database.dao.*;
import miet.rooms.api.schedule.data.frontdata.FilteredEvent;
import miet.rooms.api.schedule.data.frontdata.FilteredData;
import miet.rooms.api.util.DateTimeHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@RestController
@CrossOrigin
@Slf4j
@RequestMapping("/filter")
public class FilterController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping(value = "/cycle")
    public FilteredData getFilteredData(@RequestParam(required = false) Long weekType,
                                        @RequestParam(required = false) Long pairId,
                                        @RequestParam(required = false) Long weekDay,
                                        @RequestParam(required = false) Long pageSize,
                                        @RequestParam(required = false) Long pageNum) {
        String queryStr = getQuery(weekType, pairId, weekDay, pageSize, pageNum);
        String queryCount = getQueryCount(weekType, pairId, weekDay);
        return getFilteredData(pageSize, pageNum, queryStr, queryCount);
    }

    @GetMapping(value = "/determined")
    public FilteredData getFilteredDataCount(@RequestParam(required = false) Long pairId,
                                             @RequestParam(required = false) String date,
                                             @RequestParam(required = false) Long pageSize,
                                             @RequestParam(required = false) Long pageNum) {
        String queryStr = getQuery(pairId, date, pageSize, pageNum);
        String queryCount = getQueryCount(pairId, date);
        return getFilteredData(pageSize, pageNum, queryStr, queryCount);
    }

    private FilteredData getFilteredData(Long pageSize, Long pageNum, String queryStr, String queryCount) {
        List<FilteredEvent> data = returnFilteredData(queryStr);
        Collections.reverse(data);
        FilteredData filteredData = new FilteredData();
        filteredData.setFilteredEventList(data);
        filteredData.setPageNum(pageNum);
        filteredData.setPageSize(pageSize);
        filteredData.setTotalAmount(getCount(queryCount));
        return filteredData;
    }

    private List<FilteredEvent> returnFilteredData(String queryStr) {
        return jdbcTemplate.query(queryStr, (rs, rowNum) -> {
            FilteredEvent filteredEvent = new FilteredEvent();
            filteredEvent.setDate(DateTimeHelper.dateToString(rs.getDate("date").toLocalDate()));
            filteredEvent.setEngageTypeId(rs.getLong("engage_type_id"));
            filteredEvent.setGroupId(rs.getLong("engaged_by_id"));
            filteredEvent.setId(rs.getLong("id"));
            filteredEvent.setPairId(rs.getLong("pair_id"));
            filteredEvent.setRoomId(rs.getLong("room_id"));
            filteredEvent.setWeekDay(rs.getLong("week_day"));
            filteredEvent.setWeekNum(rs.getLong("week_num"));
            filteredEvent.setWeekType(rs.getLong("week_type"));
            return filteredEvent;
        });
    }

    private Long getCount(String queryCount) {
        log.info(queryCount);
        return jdbcTemplate.queryForObject(
                queryCount, new Object[]{}, Long.class);
    }

    private String getQuery(Long weekType,
                            Long pairId,
                            Long weekDay,
                            Long pageSize,
                            Long pageNum) {
        StringBuilder query = new StringBuilder("select * from ");
        query.append("schedule.all_data");

        query.append(" where ");

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
                    .append(weekDay);
        }
        query.append(" and engaged_by_id is null")
                .append(" order by date asc")
                .append(" limit ")
                .append(pageSize)
                .append(" offset ")
                .append((pageNum - 1) * pageSize);
        String queryStr = query.toString().trim()
                .replaceAll(" +", " ")
                .replaceAll("and and", "and")
                .replaceAll("where and", "where");
        log.info(queryStr);
        return queryStr;
    }

    private String getQuery(Long pairId,
                            String date,
                            Long pageSize,
                            Long pageNum) {
        StringBuilder query = new StringBuilder("select * from ");
        query.append("schedule.all_data");

        query.append(" where ");

        if (pairId != null) {
            query.append("pair_id = ")
                    .append(pairId)
                    .append(" and ");
        }
        if (date != null) {
            query.append("date = to_date('")
                    .append(date)
                    .append("', 'dd.MM.yyyy')");
        }

        query.append(" and engaged_by_id is null")
                .append(" order by date asc")
                .append(" limit ")
                .append(pageSize)
                .append(" offset ")
                .append((pageNum - 1) * pageSize);
        String queryStr = query.toString().trim()
                .replaceAll(" +", " ")
                .replaceAll("and and", "and")
                .replaceAll("where and", "where");
        log.info(queryStr);
        return queryStr;
    }

    private String getQueryCount(Long weekType,
                                 Long pairId,
                                 Long weekDay) {
        StringBuilder query = new StringBuilder("select count(*) from ");
        query.append("schedule.all_data");

        query.append(" where ");

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
                    .append(weekDay);
        }
        query.append(" and engaged_by_id is null");
        String queryStr = query.toString().trim()
                .replaceAll(" +", " ")
                .replaceAll("and and", "and")
                .replaceAll("where and", "where");
        log.info(queryStr);
        return queryStr;
    }

    private String getQueryCount(Long pairId,
                                 String date) {
        StringBuilder query = new StringBuilder("select count(*) from ");
        query.append("schedule.all_data");

        query.append(" where ");

        if (pairId != null) {
            query.append("pair_id = ")
                    .append(pairId)
                    .append(" and ");
        }
        if (date != null) {
            query.append("date = to_date('")
                    .append(date)
                    .append("', 'dd.MM.yyyy')");
        }

        query.append(" and engaged_by_id is null");
        String queryStr = query.toString().trim()
                .replaceAll(" +", " ")
                .replaceAll("and and", "and")
                .replaceAll("where and", "where");
        log.info(queryStr);
        return queryStr;
    }
}
