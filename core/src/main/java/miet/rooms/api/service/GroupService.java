package miet.rooms.api.service;

import miet.rooms.repository.jpa.dao.GroupDao;
import miet.rooms.repository.jpa.entity.Group;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Map<Long, Object> findAll() throws JSONException {
        Map<Long, Object> groupConfig = new HashMap<>();
        List<Group> groups = groupDao.findAll();
        for(Group group : groups) {
            groupConfig.put(group.getId(), group.getName());
        }
        return groupConfig;
    }

    public void saveAll(List<Group> groups) {
        groupDao.saveAll(groups);
    }

    public Group findById(Long id) {
        return groupDao.findAllById(id);
    }
}
