package miet.rooms.security.service;

import miet.rooms.security.jpa.dao.UserDao;
import miet.rooms.security.jpa.entity.User;
import miet.rooms.security.util.CrmUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CrmUserDetailsService implements UserDetailsService {

    private final UserDao userDao;

    @Autowired
    public CrmUserDetailsService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userDao.findByUserLogin(userName);
        if (user == null) {
            throw new UsernameNotFoundException("UserName " + userName + " not found");
        }
        return new CrmUserDetails(user);
    }

}
