package miet.rooms.api.schedule.data.database.controller;

import miet.rooms.api.schedule.data.database.dao.GroupDao;
import miet.rooms.api.schedule.data.database.entity.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/group")
public class GroupController {

    @Autowired
    private GroupDao groupDao;

    @GetMapping
    public Group getGroupByName(@RequestParam String groupName) {
        return groupDao.findAllByName(groupName);
    }
}
