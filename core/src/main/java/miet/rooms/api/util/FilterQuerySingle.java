package miet.rooms.api.util;

import lombok.extern.slf4j.Slf4j;
import miet.rooms.api.web.income.FilterSingleIncome;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Slf4j
public class FilterQuerySingle {

    private StringBuilder query;

    String getQuerySingleData(FilterSingleIncome singleIncome, Long pageNum, Long pageSize) {
        String queryStr = new QueryBuilderSingle()
                .appendSelects()
                .appendFrom()
                .appendParameters(singleIncome)
                .appendGroupBy()
                .appendLimit(pageNum, pageSize)
                .build();
        log.info(queryStr);
        return queryStr;
    }

    String getQuerySingleCount(FilterSingleIncome singleIncome) {
        String queryStr = new QueryBuilderSingle()
                .appendSelectCount()
                .appendFrom()
                .appendParameters(singleIncome)
                .appendGroupBy()
                .build();
        log.info(queryStr);
        return queryStr;
    }

    private class QueryBuilderSingle {
        private QueryBuilderSingle appendSelects() {
            query = new StringBuilder();
            query.append("select array_agg(ad.id)                                              as events,\n")
                    .append("       ad.date,\n")
                    .append("       ad.week_num,\n")
                    .append("       day.week_day_name,\n")
                    .append("       wt.week_type_name,\n")
                    .append("       concat(pairs.name, ': ', pairs.time_from, '-', pairs.time_to) as pair_info,\n")
                    .append("       r.id,\n")
                    .append("       r.name                                                        as room_name,\n")
                    .append("       r.capacity,\n")
                    .append("       sh.name                                                       as building_name,\n")
                    .append("       sh.floor,\n")
                    .append("       r_type.name                                                   as room_type_name\n");
            return this;
        }

        private QueryBuilderSingle appendSelectCount() {
            query.append("select count(*)\n");
            return this;
        }

        private QueryBuilderSingle appendFrom() {
            query.append("from schedule.all_data ad\n")
                    .append("         inner join locations.rooms r on r.id = ad.room_id\n")
                    .append("         inner join locations.schemes sh on sh.id = r.scheme_id\n")
                    .append("         inner join time_desc.week_days day on day.id = ad.week_day\n")
                    .append("         inner join locations.room_type r_type on r.room_type_id = r_type.id\n")
                    .append("         inner join time_desc.pairs pairs on ad.pair_id = pairs.id\n")
                    .append("         inner join time_desc.week_types wt on ad.week_type = wt.id\n");
            return this;
        }

        private QueryBuilderSingle appendParameters(FilterSingleIncome singleIncome) {
            query.append(" where ")
                    .append("date = to_date('").append(singleIncome.getDate()).append("', 'dd.MM.yyyy') and ");

            if (singleIncome.getWeekType() != null)
                query.append("ad.week_type = ").append(singleIncome.getWeekType()).append(" and ");
            if (singleIncome.getWeekNum() != null)
                query.append("ad.week_num = ").append(singleIncome.getWeekNum()).append(" and ");
            if (singleIncome.getBuilding() != null)
                query.append("sh.building = ").append(singleIncome.getBuilding()).append(" and ");
            if (singleIncome.getFloor() != null)
                query.append("sh.floor = ").append(singleIncome.getFloor());
            if (singleIncome.getRoomTypeId() != null)
                query.append("r_type.id = ").append(singleIncome.getRoomTypeId()).append(" and ");
            if (singleIncome.getRoomId() != null)
                query.append("r.id = ").append(singleIncome.getRoomId()).append(" and ");
            if (singleIncome.getCapacity() != null)
                query.append("r.capacity = ").append(singleIncome.getCapacity()).append(" and ");
            if (singleIncome.getPairId() != null)
                query.append("pairs.id = ").append(singleIncome.getPairId()).append(" and ");
            if (singleIncome.getWeekDay() != null)
                query.append("day.id = ").append(singleIncome.getWeekDay()).append(" and ");
            query.append(" and ad.engaged_by_id is null\n");
            return this;
        }

        private QueryBuilderSingle appendGroupBy() {
            query.append("group by pairs.id,\n")
                    .append("         day.id,\n")
                    .append("         r.id,\n")
                    .append("         sh.id,\n")
                    .append("         r_type.name,\n")
                    .append("         ad.date,\n")
                    .append("         ad.week_num,\n")
                    .append("         wt.week_type_name\n")
                    .append(" order by ad.date asc");
            return this;
        }

        private QueryBuilderSingle appendLimit(Long pageNum, Long pageSize) {
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