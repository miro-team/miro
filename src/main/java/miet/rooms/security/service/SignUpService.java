package miet.rooms.security.service;

import javax.annotation.PostConstruct;

import miet.rooms.security.jpa.dao.UserDao;
import miet.rooms.security.jpa.dao.UserRolesDao;
import miet.rooms.security.jpa.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class SignUpService {

    @Autowired
    private UserDao userDao;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private UserRolesDao userRolesDao;

    public User addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userDao.save(user);
    }

    /**
     * set up a default user with two roles USER and ADMIN
     */
    @PostConstruct
    private void setupDefaultUser() {
        //-- just to make sure there is an ADMIN user exist in the database for testing purpose
//        if (userRepository.count() == 0) {
//        UserRole.builder().name("USER").build();
//        UserRole.builder().name("ADMIN").build();
//        userRolesRepository.save(role);
//        userRolesRepository.save(role1);
//        userRepository.save(
//                User.builder()
//                        .userLogin("crmadmin")
//                        .password(passwordEncoder.encode("adminpass"))
//                        .role(
//                                userRolesRepository.findById(9L).get()/*,
//                                userRolesRepository.findById(10L).get()*/
//
//                        )
//                        .build()
//        );
//        }
    }


}
