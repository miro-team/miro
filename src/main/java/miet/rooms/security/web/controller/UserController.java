package miet.rooms.security.web.controller;

import miet.rooms.security.jpa.entity.User;
import miet.rooms.security.service.UserService;
import miet.rooms.security.web.exception.UserNotFoundException;
import miet.rooms.security.web.response.UserInformation;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ResponseBody
    @GetMapping("/user")
    public UserInformation getUserByToken() throws UserNotFoundException {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByUserName(userName);

        if (user == null) {
            throw new UserNotFoundException();
        }
        return UserInformation.builder()
                .username(user.getUserLogin())
                .fullname(user.getSurname() + " " + user.getName() + " " + user.getPatronymic())
                .build();
    }
}