package miet.rooms.security.jpa.dao;

import miet.rooms.security.jpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User, Long> {
    User findAllByUsername(@Param("username") String username);
}
