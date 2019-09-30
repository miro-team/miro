package miet.rooms.security.web.controller;

import miet.rooms.security.jpa.entity.User;
import miet.rooms.security.service.UserService;
import miet.rooms.security.web.exception.UserNotFoundException;
import miet.rooms.security.web.response.UserInformation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ResponseBody
    @GetMapping("/user")
    public UserInformation getUserByToken(@RequestHeader("Authorization") String authorizationHeader) throws UserNotFoundException {
        String token = authorizationHeader.replace("Bearer ", "").trim();
        User user = userService.getUserByToken(token);
        if (user == null) {
            throw new UserNotFoundException();
        }
        UserInformation userInformation = new UserInformation();
        userInformation.setUsername(user.getUserLogin());
        userInformation.setFullname(user.getSurname() + " " + user.getName() + " " + user.getPatronymic());
        return userInformation;
    }
}
