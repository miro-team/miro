package miet.rooms.repository.jpa.dao;

import miet.rooms.repository.jpa.entity.Reservation;
import miet.rooms.security.jpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationDao extends JpaRepository<Reservation, Long> {
    void deleteAllByEngId(Long engId);

    List<Reservation> findAllByCreatedBy(User createdBy);
}
