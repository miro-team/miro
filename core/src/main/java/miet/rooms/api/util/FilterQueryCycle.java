package miet.rooms.api.util;

import lombok.extern.slf4j.Slf4j;
import miet.rooms.api.service.AllDataService;
import miet.rooms.api.service.WeekTypeService;
import miet.rooms.api.web.income.FilterCycleIncome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class FilterQueryCycle {

    private StringBuilder query;
    private AllDataService allDataService;
    private WeekTypeService weekTypeService;

    @Autowired
    public FilterQueryCycle(AllDataService allDataService, WeekTypeService weekTypeService) {
        this.allDataService = allDataService;
        this.weekTypeService = weekTypeService;
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
                .appendGroupBy()
                .build();
        log.info(queryStr);
        return queryStr;
    }

    private class QueryBuilderCycle {
        private List<String> dates = new ArrayList<>();

        private QueryBuilderCycle appendSelects() {
            query = new StringBuilder();
            query.append("select array_agg(ad.id)                 as events,\n")
                    .append("       day.week_day_name,\n")
                    .append("       concat(pairs.name, ': ', pairs.time_from, '-', pairs.time_to)                       as pair_info,\n")
                    .append("       r.id,\n")
                    .append("       r.name                           as room_name,\n")
                    .append("       r.capacity,\n")
                    .append("       sh.name                          as building_name,\n")
                    .append("       sh.floor,\n")
                    .append("       r_type.name                      as room_type_name\n");
            return this;
        }

        private QueryBuilderCycle appendFrom() {
            query.append("from schedule.all_data ad\n")
                    .append("         inner join locations.rooms r on r.id = ad.room_id\n")
                    .append("         inner join locations.schemes sh on sh.id = r.scheme_id\n")
                    .append("         inner join time_desc.week_days day on day.id = ad.week_day\n")
                    .append("         inner join locations.room_type r_type on r.room_type_id = r_type.id\n")
                    .append("         inner join time_desc.pairs pairs on ad.pair_id = pairs.id\n");
            return this;
        }

        private QueryBuilderCycle appendSelectCount() {
            query.append("select count(*)\n");
            return this;
        }

        private QueryBuilderCycle appendParameters(FilterCycleIncome cycleIncome) {
            query.append(" where ");
            if (cycleIncome.getBuilding() != null)
                query.append("sh.building = ").append(cycleIncome.getBuilding()).append(" and ");
            if (cycleIncome.getFloor() != null)
                query.append("sh.floor = ").append(cycleIncome.getFloor());
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

            Long weekNum = allDataService.determineCurrentWeek(LocalDate.now());
            List<String> dates = generateDates(cycleIncome.getWeekType(), cycleIncome.getWeekDay(), weekNum);
            query.append("ad.date in (");
            for (String d : dates) {
                query.append("to_date('")
                        .append(d)
                        .append("', 'dd.MM.yyyy'),\n");
            }
            query.append(") ");

            query.append(" and ad.engaged_by_id is null\n");
            return this;
        }

        private List<String> generateDates(Long weekType, Long weekDay, Long weekNum) {
            dates = new ArrayList<>();
            Long weeksToAdd = weekTypeService.getWeeksToAdd(weekType);
            LocalDate startDate = weekNum == 1L ? allDataService.getSemesterStartDate() : LocalDate.now();

            if (weekDay == null) {
                for (int week = 0; week < 18 - weekNum.intValue(); week += weeksToAdd) {
                    addDate(weekNum, startDate, week, DayOfWeek.MONDAY);
                    addDate(weekNum, startDate, week, DayOfWeek.TUESDAY);
                    addDate(weekNum, startDate, week, DayOfWeek.WEDNESDAY);
                    addDate(weekNum, startDate, week, DayOfWeek.THURSDAY);
                    addDate(weekNum, startDate, week, DayOfWeek.FRIDAY);
                    addDate(weekNum, startDate, week, DayOfWeek.SATURDAY);
                    addDate(weekNum, startDate, week, DayOfWeek.SUNDAY);
                }
            } else {
                for (int week = 0; week < 18 - weekNum.intValue(); week += weeksToAdd) {
                    addDate(weekNum, startDate, week, DayOfWeek.of(weekDay.intValue()));
                }
            }
            return dates;
        }

        private void addDate(Long weekNum, LocalDate startDate, int week, DayOfWeek dayOfWeek) {
            if (startDate.getDayOfWeek().getValue() < startDate.with(dayOfWeek).getDayOfWeek().getValue())
                dates.add(DateTimeHelper.dateToString(startDate.with(dayOfWeek)));
            else if (weekNum == 1L)
                dates.add(DateTimeHelper.dateToString(startDate.with(dayOfWeek)));
            else
                dates.add(DateTimeHelper.dateToString(startDate.with(dayOfWeek).plusWeeks(week)));
        }

        private QueryBuilderCycle appendGroupBy() {
            query.append("group by pairs.id,\n")
                    .append("         day.id,\n")
                    .append("         r.id,\n")
                    .append("         sh.id,\n")
                    .append("         r_type.name\n");
//                .append(" having array_length(array_agg(id), 1) = ")
//                .append(amount)
            return this;
        }

        private QueryBuilderCycle appendLimit(Long pageNum, Long pageSize) {
            query.append(" limit ")
                    .append(pageSize)
                    .append(" offset ")
                    .append((pageNum - 1) * pageSize);
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
