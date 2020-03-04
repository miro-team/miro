package miet.rooms.api.service;

import miet.rooms.repository.jpa.dao.WeekTypeDao;
import miet.rooms.repository.jpa.entity.WeekType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeekTypeService {

    private WeekTypeDao weekTypeDao;

    @Autowired
    public WeekTypeService(WeekTypeDao weekTypeDao) {
        this.weekTypeDao = weekTypeDao;
    }

    public Long getWeeksToAdd(Long id) {
        return weekTypeDao.findAllById(id).getWeeksToNext();
    }

    public String getWeekTypeName(Long weekTypeCodeNum) {
        return weekTypeDao.findAllByWeekTypeCodeNum(weekTypeCodeNum).getWeekTypeName();
    }

    public WeekType getWeekTypeByCodeNum(Long weekTypeCodeNum) {
        return weekTypeDao.findAllByWeekTypeCodeNum(weekTypeCodeNum);
    }
}
