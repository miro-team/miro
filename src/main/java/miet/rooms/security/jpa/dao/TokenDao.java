package miet.rooms.security.jpa.dao;

import miet.rooms.security.jpa.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenDao extends JpaRepository<Token, Long> {
    Token findAllByToken(@Param("token") String token);

    void removeAllByToken(@Param("token") String token);
}
