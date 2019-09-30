package miet.rooms.api.service;

import miet.rooms.repository.jpa.dao.AllDataDao;
import miet.rooms.repository.jpa.entity.AllData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AllDataService {

    private AllDataDao allDataDao;

    @Autowired
    public AllDataService(AllDataDao allDataDao) {
        this.allDataDao = allDataDao;
    }

    public Long determineCurrentWeek(LocalDate date) {
        AllData currentAllData = allDataDao.findFirstByDate(date);
        return currentAllData != null ? currentAllData.getWeekNum() : 1L;
    }

    public LocalDate getSemesterStartDate() {
        return allDataDao.findFirstByOrderByDateAsc().getDate();
    }

    public Long getLastWeek() {
        return allDataDao.findFirstByOrderByWeekNumDesc().getWeekNum();
    }

    public void removeByWeekNum(Long weekNum) {
        allDataDao.deleteByWeekNum(weekNum);
    }

    public List<AllData> getAllByEngageTypeId(Long engageTypeId) {
        return allDataDao.findAllByEngageType_Id(engageTypeId);
    }

    public void save(AllData allData) {
        allDataDao.save(allData);
    }

    public AllData findById(Long id) {
        return allDataDao.findById(id).orElse(null);
    }
}
