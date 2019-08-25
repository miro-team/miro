package miet.rooms.repository.jpa.dao;

import miet.rooms.repository.jpa.entity.Reservation;
import miet.rooms.security.jpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationDao extends JpaRepository<Reservation, Long> {
    Reservation findAllById(@Param("id") Long id);

    void deleteAllById(@Param("id") Long id);

    List<Reservation> findAllByCreatedBy(@Param("createdBy") User createdBy);
}
