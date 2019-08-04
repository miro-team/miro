package miet.rooms.api.service;

import miet.rooms.api.web.income.FilterCycleIncome;
import miet.rooms.repository.jdbc.dao.FilterCycleDao;
import miet.rooms.repository.jdbc.dao.FilterSingleDao;
import miet.rooms.repository.jdbc.model.FilteredDataCycle;
import miet.rooms.repository.jdbc.model.FilteredDataSingle;
import miet.rooms.api.util.FilterQuery;
import miet.rooms.api.web.income.FilterSingleIncome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FilterService {
    private final FilterQuery filterQuery;
    private final FilterSingleDao filterSingleDao;
    private final FilterCycleDao filterCycleDao;

    @Autowired
    public FilterService(FilterQuery filterQuery, FilterSingleDao filterSingleDao, FilterCycleDao filterCycleDao) {
        this.filterQuery = filterQuery;
        this.filterSingleDao = filterSingleDao;
        this.filterCycleDao = filterCycleDao;
    }

    public FilteredDataSingle getFilteredDataSingle(FilterSingleIncome singleIncome, Long pageNum, Long pageSize) {
        String queryData = filterQuery.getQuerySingleData(singleIncome, pageSize, pageNum);
        String queryCount = filterQuery.getQuerySingleCount(singleIncome);
        return filterSingleDao.getFilteredDataSingle(pageSize, pageNum, queryData, queryCount);
    }

    public FilteredDataCycle getFilteredDataCycle(FilterCycleIncome cycleIncome, Long pageNum, Long pageSize) {
        String queryData = filterQuery.getQueryCycleData(cycleIncome, pageSize, pageNum);
        String queryCount = filterQuery.getQueryCycleCount(cycleIncome);
        return filterCycleDao.getFilteredDataCycle(pageSize, pageNum, queryData, queryCount, cycleIncome.getWeekType());
    }
}
