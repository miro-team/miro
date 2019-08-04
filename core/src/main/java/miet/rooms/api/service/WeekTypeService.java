package miet.rooms.api.service;

import miet.rooms.repository.jpa.dao.WeekTypeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeekTypeService {

    private WeekTypeDao weekTypeDao;

    @Autowired
    public WeekTypeService(WeekTypeDao weekTypeDao) {
        this.weekTypeDao = weekTypeDao;
    }

    public Long getWeeksToAdd(Long weekTypeCodeNum) {
        return weekTypeDao.findAllByWeekTypeCodeNum(weekTypeCodeNum).getWeeksToNext();
    }

    public String getWeekTypeName(Long weekTypeCodeNum) {
        return weekTypeDao.findAllByWeekTypeCodeNum(weekTypeCodeNum).getWeekTypeName();
    }
}
