package miet.rooms.api.util;

import lombok.extern.slf4j.Slf4j;
import miet.rooms.api.service.PeriodicityService;
import miet.rooms.api.web.income.FilterCycleIncome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.stream.Collectors;

@Component
@Slf4j
public class FilterQueryCycle {

    private final PeriodicityService periodicityService;

    @Autowired
    public FilterQueryCycle(PeriodicityService periodicityService) {
        this.periodicityService = periodicityService;
    }

    String getQueryCycleData(FilterCycleIncome cycleIncome, Long pageSize, Long pageNum) {
        String queryStr = new QueryBuilderCycle()
                .appendSelects()
                .appendFrom()
                .appendParameters(cycleIncome)
                .appendGroupBy()
                .appendLimit(pageNum, pageSize)
                .build();
        log.info(queryStr);
        return queryStr;
    }

    String getQueryCycleCount(FilterCycleIncome cycleIncome) {
        String queryStr = new QueryBuilderCycle()
                .appendSelectCount()
                .appendFrom()
                .appendParameters(cycleIncome)
                .build();
        log.info(queryStr);
        return queryStr;
    }

    private class QueryBuilderCycle {
        private StringBuilder query;

        private QueryBuilderCycle appendSelects() {
            query = new StringBuilder();
            query.append("select array_agg(ad.id)                 as events,\n")
                    .append("       array_agg(ad.date)            as dates,\n")
                    .append("       day.day_code,\n")
                    .append("       pairs.name as pair_info,\n")
                    .append("       r.id,\n")
                    .append("       r.name                           as room_name,\n")
                    .append("       r.capacity,\n")
                    .append("       sh.name                          as building_name,\n")
                    .append("       sh.floor,\n")
                    .append("       r_type.name                      as room_type_name\n");
            return this;
        }

        private QueryBuilderCycle appendFrom() {
            query.append("from schedule.events ad\n")
                    .append("         inner join locations.rooms r on r.id = ad.room_id\n")
                    .append("         inner join locations.schemes sh on sh.id = r.scheme_id\n")
                    .append("         inner join time_desc.week_days day on day.id = ad.week_day\n")
                    .append("         inner join locations.room_type r_type on r.room_type_id = r_type.id\n")
                    .append("         inner join time_desc.pairs pairs on ad.pair_id = pairs.id\n");
            return this;
        }

        private QueryBuilderCycle appendSelectCount() {
            query = new StringBuilder();
            query.append("select count(*)\n");
            return this;
        }

        private QueryBuilderCycle appendParameters(FilterCycleIncome cycleIncome) {
            query.append(" where ");
            if (cycleIncome.getPeriodicity() != null)
                query.append("ad.week_type in ( ")
                        .append(periodicityService.getWeekTypesIdById(cycleIncome.getPeriodicity()).stream()
                                .map(Object::toString)
                                .collect(Collectors.joining(",")))
                        .append(") and ");
            if (cycleIncome.getBuilding() != null)
                query.append("sh.building = \'").append(cycleIncome.getBuilding()).append("\' and ");
            if (cycleIncome.getFloor() != null)
                query.append("sh.floor = ").append(cycleIncome.getFloor()).append(" and ");
            if (cycleIncome.getRoomTypeId() != null)
                query.append("r_type.id = ").append(cycleIncome.getRoomTypeId()).append(" and ");
            if (cycleIncome.getRoomId() != null)
                query.append("r.id = ").append(cycleIncome.getRoomId()).append(" and ");
            if (cycleIncome.getCapacity() != null)
                query.append("r.capacity = ").append(cycleIncome.getCapacity()).append(" and ");
            if (cycleIncome.getPairId() != null)
                query.append("pairs.id = ").append(cycleIncome.getPairId()).append(" and ");
            if (cycleIncome.getWeekDay() != null)
                query.append("day.id = ").append(cycleIncome.getWeekDay()).append(" and ");

            query.append("and ad.date >= to_date('")
                    .append(DateTimeHelper.dateToString(LocalDate.now()))
                    .append("', 'dd.MM.yyyy') and eng_id is null\n");
            return this;
        }

        private QueryBuilderCycle appendGroupBy() {
            query.append("group by pairs.id,\n")
                    .append("         day.id,\n")
                    .append("         r.id,\n")
                    .append("         sh.id,\n")
                    .append("         r_type.name\n");
            return this;
        }

        private QueryBuilderCycle appendLimit(Long pageNum, Long pageSize) {
            query.append(" limit ")
                    .append(pageSize)
                    .append(" offset ")
                    .append(pageNum);
            return this;
        }


        private String build() {
            String queryStr = query.toString().trim()
                    .replaceAll(" +", " ")
                    .replaceAll("and and", "and");
            return queryStr.replaceAll("and \\)", StringUtils.countOccurrencesOf(queryStr, "\\(") == 2 ? ")) " : ") ")
                    .replaceAll("where and", "where");
        }
    }
}
