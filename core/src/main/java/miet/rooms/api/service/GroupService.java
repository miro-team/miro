package miet.rooms.api.service;

import miet.rooms.repository.jpa.dao.GroupDao;
import miet.rooms.repository.jpa.entity.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupService {

    private final GroupDao groupDao;

    @Autowired
    public GroupService(GroupDao groupDao) {
        this.groupDao = groupDao;
    }

    public Group getGroupByName(String groupName) {
        return groupDao.findAllByName(groupName);
    }
}
