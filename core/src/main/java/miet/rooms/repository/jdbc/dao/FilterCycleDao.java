//package miet.rooms.repository.jdbc.dao;
//
//import miet.rooms.api.service.PeriodicityService;
//import miet.rooms.api.service.WeekTypeService;
//import miet.rooms.api.util.DateTimeHelper;
//import miet.rooms.repository.jdbc.model.FilteredDataCycle;
//import miet.rooms.repository.jdbc.model.FilteredEvent;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.EmptyResultDataAccessException;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Repository;
//
//import java.sql.Date;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Repository
//public class FilterCycleDao {
//
//    private final JdbcTemplate jdbcTemplate;
//    private final PeriodicityService periodicityService;
//
//    @Autowired
//    public FilterCycleDao(JdbcTemplate jdbcTemplate, PeriodicityService periodicityService) {
//        this.jdbcTemplate = jdbcTemplate;
//        this.periodicityService = periodicityService;
//    }
//
//    public FilteredDataCycle getFilteredDataCycle(Long pageSize, Long pageNum, String queryStr, String queryCount, Long periodicityId) {
//        List<FilteredEvent> data = getFilteredEventsCycle(queryStr, periodicityId);
//        Collections.reverse(data);
//        return FilteredDataCycle.builder()
//                .filteredEventList(data)
//                .pageNum(pageNum)
//                .pageSize(pageSize)
//                .totalAmount(getCount(queryCount))
//                .build();
//    }
//
//    private List<FilteredEvent> getFilteredEventsCycle(String queryStr, Long periodicityId) {
//        String periodicity = periodicityService.findById(periodicityId).getName();
//        return jdbcTemplate.query(queryStr, (rs, rowNum) -> FilteredEvent.builder()
//                .events(new Integer[]{rs.getInt("events")})
//                .date(
//                        Arrays.stream((Date[]) rs.getArray("date").getArray())
//                                .map(Date::toLocalDate)
//                                .map(DateTimeHelper::dateToString)
//                                .toArray(String[]::new)
//                )
//                .pair(rs.getString("pair_info"))
//                .weekDay(rs.getString("week_day_name"))
//                .weekNum(rs.getLong("week_num"))
//                .periodicity(periodicity)
//                .room(rs.getString("room_name"))
//                .capacity(rs.getLong("capacity"))
//                .roomType(rs.getString("room_type_name"))
//                .building(rs.getString("building_name"))
//                .floor(String.valueOf(rs.getLong("floor")))
//                .build());
//    }
//
//    private Long getCount(String queryCount) {
//        try {
//            return jdbcTemplate.queryForObject(queryCount, Long.class);
//        } catch (EmptyResultDataAccessException ex) {
//            return 0L;
//        }
//    }
//}
