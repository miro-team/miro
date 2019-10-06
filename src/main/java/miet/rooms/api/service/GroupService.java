package miet.rooms.api.service;

import miet.rooms.api.web.response.ConfigResponse;
import miet.rooms.repository.jpa.dao.GroupDao;
import miet.rooms.repository.jpa.entity.Group;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<Object> findAllMapping() throws JSONException {
        return groupDao.findAll().stream()
                .map(r -> {
                    ConfigResponse response = new ConfigResponse();
                    response.setId(r.getId());
                    response.setName(r.getName());
                    return response;
                })
                .collect(Collectors.toList());
    }

    public void saveAll(List<Group> groups) {
        groupDao.saveAll(groups);
    }

    public Group findById(Long id) {
        return groupDao.findAllById(id);
    }
}
