package miet.rooms.api.web.controller;

import miet.rooms.repository.jpa.dao.GroupDao;
import miet.rooms.repository.jpa.entity.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/group")
public class GroupController {

    private final GroupDao groupDao;

    @Autowired
    public GroupController(GroupDao groupDao) {
        this.groupDao = groupDao;
    }

    @GetMapping
    public Group getGroupByName(@RequestParam String groupName) {
        return groupDao.findAllByName(groupName);
    }
}
