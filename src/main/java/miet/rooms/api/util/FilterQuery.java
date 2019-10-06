package miet.rooms.api.util;

import lombok.extern.slf4j.Slf4j;
import miet.rooms.api.web.income.FilterCycleIncome;
import miet.rooms.api.web.income.FilterSingleIncome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FilterQuery {

    private final FilterQuerySingle querySingle;
    private final FilterQueryCycle queryCycle;

    @Autowired
    public FilterQuery(FilterQuerySingle querySingle, FilterQueryCycle queryCycle) {
        this.querySingle = querySingle;
        this.queryCycle = queryCycle;
    }

    public String getQuerySingleData(FilterSingleIncome singleIncome, Long pageSize, Long pageNum) {
        return querySingle.getQuerySingleData(singleIncome, pageSize, pageNum);
    }

    public String getQuerySingleCount(FilterSingleIncome singleIncome) {
        return querySingle.getQuerySingleCount(singleIncome);
    }

    public String getQueryCycleData(FilterCycleIncome cycleIncome, Long pageSize, Long pageNum) {
        return queryCycle.getQueryCycleData(cycleIncome, pageSize, pageNum);
    }

    public String getQueryCycleCount(FilterCycleIncome cycleIncome) {
        return queryCycle.getQueryCycleCount(cycleIncome);
    }
}
