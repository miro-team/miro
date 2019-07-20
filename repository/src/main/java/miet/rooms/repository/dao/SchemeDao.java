package miet.rooms.repository.dao;

import miet.rooms.repository.entity.Scheme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchemeDao extends JpaRepository<Scheme, Long> {
    List<Scheme> findAllByBuilding(@Param("building") Long building);

    List<Scheme> findAllByFloor(@Param("floor") Long floor);

    @Query(value = "select scheme from Scheme scheme where scheme.building=:building and scheme.floor=:floor and scheme.name<>'УВЦ'")
    Scheme findAllByFloorAndBuilding(@Param("floor") Long floor, @Param("building") Long building);

    Scheme findAllById(@Param("id") Long id);
}
