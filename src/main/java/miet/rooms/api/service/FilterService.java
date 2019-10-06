package miet.rooms.api.service;

import miet.rooms.api.util.FilterQuery;
import miet.rooms.api.web.income.FilterCycleIncome;
import miet.rooms.api.web.income.FilterSingleIncome;
import miet.rooms.repository.jdbc.dao.FilterDao;
import miet.rooms.repository.jdbc.model.FilteredData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FilterService {
    private final FilterQuery filterQuery;
    private final FilterDao filterDao;

    @Autowired
    public FilterService(FilterQuery filterQuery, FilterDao filterDao) {
        this.filterQuery = filterQuery;
        this.filterDao = filterDao;
    }

    public FilteredData getFilteredData(FilterSingleIncome incomeData, Long pageNum, Long pageSize) {
        String queryData = filterQuery.getQuerySingleData(incomeData, pageSize, pageNum);
        String queryCount = filterQuery.getQuerySingleCount(incomeData);
        return filterDao.getFilteredDataSingle(pageSize, pageNum, queryData, queryCount);
    }

    public FilteredData getFilteredData(FilterCycleIncome incomeData, Long pageNum, Long pageSize) {
        String queryData = filterQuery.getQueryCycleData(incomeData, pageSize, pageNum);
        String queryCount = filterQuery.getQueryCycleCount(incomeData);
        return filterDao.getFilteredData(pageSize, pageNum, queryData, queryCount);
    }
}
