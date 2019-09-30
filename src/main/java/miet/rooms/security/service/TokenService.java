package miet.rooms.security.service;

import miet.rooms.security.jpa.dao.TokenDao;
import miet.rooms.security.jpa.dao.UserDao;
import miet.rooms.security.jpa.entity.Token;
import miet.rooms.security.jpa.entity.User;
import miet.rooms.security.web.request.Credentials;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TokenService {

    private final TokenDao tokenDao;
    private final UserDao userDao;

    public TokenService(TokenDao tokenDao, UserDao userDao) {
        this.tokenDao = tokenDao;
        this.userDao = userDao;
    }

    public void saveToken(String jwtToken, Credentials credentials) {
        Token token = new Token();
        token.setToken(jwtToken);

        User user = userDao.findAllByUserLogin(credentials.getUsername());
        token.setUser(user);

        tokenDao.save(token);
    }

    @Transactional
    public void removeToken(String token) {
        tokenDao.removeAllByToken(token);
    }
}
