package miet.rooms.repository.jdbc.dao;

import miet.rooms.api.service.PeriodicityService;
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
    private final PeriodicityService periodicityService;

    @Autowired
    public FilterDao(JdbcTemplate jdbcTemplate, PeriodicityService periodicityService) {
        this.jdbcTemplate = jdbcTemplate;
        this.periodicityService = periodicityService;
    }

    public FilteredData getFilteredData(Long pageSize, Long pageNum, String queryData, String queryCount, Long periodicityId) {
        List<FilteredEvent> data = getFilteredEventsSingle(queryData, periodicityId);
        Collections.reverse(data);
        return FilteredData.builder()
                .filteredEventList(data)
                .pageNum(pageNum)
                .pageSize(pageSize)
                .totalAmount(getCount(queryCount))
                .build();
    }

    private List<FilteredEvent> getFilteredEventsSingle(String queryStr, Long periodicityId) {
        String periodicity = periodicityService.findById(periodicityId).getName();
        return jdbcTemplate.query(queryStr, (rs, rowNum) -> FilteredEvent.builder()
                .events((Integer[]) rs.getArray("events").getArray())
                .date(
                        Arrays.stream((Date[]) rs.getArray("dates").getArray())
                                .map(Date::toLocalDate)
                                .map(DateTimeHelper::dateToString)
                                .toArray(String[]::new)
                )
                .pair(rs.getString("pair_info"))
                .weekDay(rs.getString("week_day_name"))
                .weekNum(rs.getLong("week_num"))
                .periodicity(periodicity)
                .periodicityId(periodicityId)
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
