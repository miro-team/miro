package miet.rooms.security.service;

import miet.rooms.security.jpa.dao.TokenDao;
import miet.rooms.security.jpa.dao.UserDao;
import miet.rooms.security.jpa.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final TokenDao tokenDao;
    private final UserDao userDao;

    public UserService(TokenDao tokenDao, UserDao userDao) {
        this.tokenDao = tokenDao;
        this.userDao = userDao;
    }

    public User getUserByToken(String token) {
        return tokenDao.findAllByToken(token).getUser();
    }

    public User getUserByUsername(String username) {
        return userDao.findAllByUsername(username);
    }
}
