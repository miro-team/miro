package miet.rooms.api.web.controller;

import miet.rooms.repository.dao.GroupDao;
import miet.rooms.repository.entity.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/group")
//@ComponentScan(basePackages = {"miet.rooms.repository.dao"})
public class GroupController {

    @Autowired
    private GroupDao groupDao;

    @GetMapping
    public Group getGroupByName(@RequestParam String groupName) {
        return groupDao.findAllByName(groupName);
    }
}
