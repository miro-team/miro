package miet.rooms.api.service;

import miet.rooms.repository.jpa.dao.AllDataDao;
import miet.rooms.repository.jpa.entity.AllData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

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
        return allDataDao.findFirstByOrOrderByDateAsc().getDate();
    }
}
