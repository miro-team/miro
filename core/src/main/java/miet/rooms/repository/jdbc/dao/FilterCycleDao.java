package miet.rooms.repository.jdbc.dao;

import miet.rooms.api.service.WeekTypeService;
import miet.rooms.repository.jdbc.model.FilteredDataCycle;
import miet.rooms.repository.jdbc.model.FilteredEventCycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
public class FilterCycleDao {

    private JdbcTemplate jdbcTemplate;
    private WeekTypeService weekTypeService;

    @Autowired
    public FilterCycleDao(JdbcTemplate jdbcTemplate, WeekTypeService weekTypeService) {
        this.jdbcTemplate = jdbcTemplate;
        this.weekTypeService = weekTypeService;
    }

    public FilteredDataCycle getFilteredDataCycle(Long pageSize, Long pageNum, String queryStr, String queryCount, Long weekType) {
        List<FilteredEventCycle> data = getFilteredEventsCycle(queryStr, weekType);
        Collections.reverse(data);
        return FilteredDataCycle.builder()
                .filteredEventCycleList(data)
                .pageNum(pageNum)
                .pageSize(pageSize)
                .totalAmount(getCount(queryCount))
                .build();
    }

    //TODO:cast to long
    //TODO:make not null to avoid unnessesary checks
    private List<FilteredEventCycle> getFilteredEventsCycle(String queryStr, Long weekType) {
        String weekTypeName = weekTypeService.getWeekTypeName(weekType);
        return jdbcTemplate.query(queryStr, (rs, rowNum) -> FilteredEventCycle.builder()
                .events((Integer[]) rs.getArray("events").getArray())
                .pair(rs.getString("pair_info"))
                .weekDay(rs.getString("week_day_name"))
                .weekType(weekTypeName)
                .room(rs.getString("room_name"))
                .capacity(rs.getLong("capacity"))
                .roomType(rs.getString("room_type_name"))
                .building(rs.getString("building_name"))
                .floor(String.valueOf(rs.getLong("floor")))
                .build());
    }

    private Long getCount(String queryCount) {
        return jdbcTemplate.queryForObject(
                queryCount, new Object[]{}, Long.class);
    }
}
