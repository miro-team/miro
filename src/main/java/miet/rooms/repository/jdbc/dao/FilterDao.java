package miet.rooms.repository.jdbc.dao;

import miet.rooms.api.util.DateTimeHelper;
import miet.rooms.repository.jdbc.model.FilteredData;
import miet.rooms.repository.jdbc.model.FilteredEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Repository
public class FilterDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FilterDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public FilteredData getFilteredData(Long pageSize, Long pageNum, String queryData, String queryCount) {
        List<FilteredEvent> data = getFilteredEvents(queryData);
        Collections.reverse(data);
        return FilteredData.builder()
                .filteredEventList(data)
                .pageNum(pageNum)
                .pageSize(pageSize)
                .pageCount(getCount(queryCount))
                .build();
    }

    public FilteredData getFilteredDataSingle(Long pageSize, Long pageNum, String queryData, String queryCount) {
        List<FilteredEvent> data = getFilteredEvents(queryData);
        Collections.reverse(data);
        return FilteredData.builder()
                .filteredEventList(data)
                .pageNum(pageNum)
                .pageSize(pageSize)
                .pageCount(getCount(queryCount))
                .build();
    }

    private List<FilteredEvent> getFilteredEvents(String queryStr) {
        return jdbcTemplate.query(queryStr, (rs, rowNum) -> FilteredEvent.builder()
                .events((Integer[]) rs.getArray("events").getArray())
                .date(
                        Arrays.stream((Date[]) rs.getArray("dates").getArray())
                                .map(Date::toLocalDate)
                                .map(DateTimeHelper::dateToString)
                                .toArray(String[]::new)
                )
                .pair(rs.getString("pair_info"))
                .weekDay(rs.getString("day_code"))
//                .weekNum(rs.getLong("week_num"))
                .room(rs.getString("room_name"))
                .capacity(rs.getLong("capacity"))
                .roomType(rs.getString("room_type_name"))
                .building(rs.getString("building_name"))
                .floor(String.valueOf(rs.getLong("floor")))
                .build());
    }

    private Long getCount(String queryCount) {
        try {
            return jdbcTemplate.queryForObject(queryCount, Long.class);
        } catch (EmptyResultDataAccessException ex) {
            return 0L;
        }
    }
}
