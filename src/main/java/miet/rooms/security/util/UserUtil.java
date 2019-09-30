package miet.rooms.security.util;

import miet.rooms.security.jpa.entity.User;
import miet.rooms.security.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class UserUtil {
    private static UserService userService;

    public UserUtil(UserService userService) {
        UserUtil.userService = userService;
    }

    public static User getUserByAuthHeader(String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "").trim();
        return userService.getUserByToken(token);
    }
}
