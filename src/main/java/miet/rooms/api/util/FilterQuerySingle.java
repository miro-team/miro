package miet.rooms.api.util;

import lombok.extern.slf4j.Slf4j;
import miet.rooms.api.service.PeriodicityService;
import miet.rooms.api.web.income.FilterSingleIncome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.stream.Collectors;

@Component
@Slf4j
public class FilterQuerySingle {

    String getQuerySingleData(FilterSingleIncome singleIncome, Long pageSize, Long pageNum) {
        String queryStr = new QueryBuilderSingle()
                .appendSelects()
                .appendFrom()
                .appendParameters(singleIncome)
                .appendOrderBy()
                .appendLimit(pageSize, pageNum)
                .build();
        log.info(queryStr);
        return queryStr;
    }

    String getQuerySingleCount(FilterSingleIncome singleIncome) {
        String queryStr = new QueryBuilderSingle()
                .appendSelectCount()
                .appendFrom()
                .appendParameters(singleIncome)
                .build();
        log.info(queryStr);
        return queryStr;
    }

    private static class QueryBuilderSingle {
        private StringBuilder query;

        private QueryBuilderSingle appendSelects() {
            query = new StringBuilder();
            query.append("select    array[ad.id]                                              as events,\n")
                    .append("       array[ad.date]                                            as dates,\n")
                    .append("       ad.week_num,\n")
                    .append("       day.day_code,\n")
                    .append("       wt.week_type_code,\n")
                    .append("       pairs.name as pair_info,\n")
                    .append("       r.id,\n")
                    .append("       r.name                                                        as room_name,\n")
                    .append("       r.capacity,\n")
                    .append("       sh.name                                                       as building_name,\n")
                    .append("       sh.floor,\n")
                    .append("       r_type.name                                                   as room_type_name\n");
            return this;
        }

        private QueryBuilderSingle appendSelectCount() {
            query = new StringBuilder();
            query.append("select count(*)\n");
            return this;
        }

        private QueryBuilderSingle appendFrom() {
            query.append("from schedule.events ad\n")
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
            if (singleIncome.getBuilding() != null)
                query.append("sh.building = \'").append(singleIncome.getBuilding()).append("\' and ");
            if (singleIncome.getFloor() != null)
                query.append("sh.floor = ").append(singleIncome.getFloor()).append(" and ");
            if (singleIncome.getRoomTypeId() != null)
                query.append("r_type.id = ").append(singleIncome.getRoomTypeId()).append(" and ");
            if (singleIncome.getRoomId() != null)
                query.append("r.id = ").append(singleIncome.getRoomId()).append(" and ");
            if (singleIncome.getCapacity() != null)
                query.append("r.capacity = ").append(singleIncome.getCapacity()).append(" and ");
            if (singleIncome.getPairId() != null)
                query.append("pairs.id = ").append(singleIncome.getPairId()).append(" and ");
            query.append(" and eng_id is null\n");
            return this;
        }

        private QueryBuilderSingle appendOrderBy() {
            query.append(" order by ad.date asc");
            return this;
        }

        private QueryBuilderSingle appendLimit(Long pageSize, Long pageNum) {
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
