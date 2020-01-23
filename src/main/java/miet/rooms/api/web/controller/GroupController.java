package miet.rooms.api.web.controller;

import miet.rooms.api.service.GroupService;
import miet.rooms.repository.jpa.dao.GroupDao;
import miet.rooms.repository.jpa.entity.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/group")
public class GroupController {

    private final GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("by-id")
    public Group getGroupByName(@RequestParam String groupName) {
        return groupService.getGroupByName(groupName);
    }
}
