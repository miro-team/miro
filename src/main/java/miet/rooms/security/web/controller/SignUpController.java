package miet.rooms.security.web.controller;

import miet.rooms.security.jpa.entity.User;
import miet.rooms.security.service.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class SignUpController {

    @Autowired
    private SignUpService signupService;

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ResponseEntity<?> signup(@RequestBody User user) {
        user.setUserLogin("lol");
        user.setPassword("lol");
//        user.setRole(UserRole.builder().name("USER").build()));
        User newUser = signupService.addUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
