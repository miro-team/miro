package miet.rooms.security.jpa.dao;

import miet.rooms.security.jpa.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRolesDao extends JpaRepository<UserRole, Long> {

}