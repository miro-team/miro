package miet.rooms.security.web.controller;

import miet.rooms.security.jpa.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RestResource {
    @RequestMapping("/api/users/me")
    public ResponseEntity<User> profile() {
        //Build some dummy data to return for testing
        org.springframework.security.core.userdetails.User user =
                (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String email = user.getUsername() + "@howtodoinjava.com";

        User profile = new User();
        profile.setName(user.getUsername());
//        profile.setEmail(email);

        return ResponseEntity.ok(profile);
    }
}