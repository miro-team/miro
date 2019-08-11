package miet.rooms.repository.jdbc.dao;

import miet.rooms.api.util.DateTimeHelper;
import miet.rooms.repository.jdbc.model.FilteredDataSingle;
import miet.rooms.repository.jdbc.model.FilteredEventSingle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
public class FilterSingleDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public FilterSingleDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public FilteredDataSingle getFilteredDataSingle(Long pageSize, Long pageNum, String queryData, String queryCount) {
        List<FilteredEventSingle> data = getFilteredEventsSingle(queryData);
        Collections.reverse(data);
        return FilteredDataSingle.builder()
                .filteredEventSingleList(data)
                .pageNum(pageNum)
                .pageSize(pageSize)
                .totalAmount(getCount(queryCount))
                .build();
    }

    private List<FilteredEventSingle> getFilteredEventsSingle(String queryStr) {
        return jdbcTemplate.query(queryStr, (rs, rowNum) -> {
            return FilteredEventSingle.builder()
                    .events((Integer[]) rs.getArray("events").getArray())
                    .date(DateTimeHelper.dateToString(rs.getDate("date").toLocalDate()))
                    .pair(rs.getString("pair_info"))
                    .weekDay(rs.getString("week_day_name"))
                    .weekNum(rs.getLong("week_num"))
                    .weekType(rs.getString("week_type_name"))
                    .room(rs.getString("room_name"))
                    .capacity(rs.getLong("capacity"))
                    .roomType(rs.getString("room_type_name"))
                    .building(rs.getString("building_name"))
                    .floor(String.valueOf(rs.getLong("floor")))
                    .build();
        });
    }

    private Long getCount(String queryCount) {
        return jdbcTemplate.queryForObject(
                queryCount, new Object[]{}, Long.class);
    }
}
