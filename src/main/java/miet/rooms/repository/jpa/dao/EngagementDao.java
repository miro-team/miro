package miet.rooms.repository.jpa.dao;

import miet.rooms.repository.jpa.entity.Engagement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EngagementDao extends JpaRepository<Engagement, Long> {

    @Query("select max(engagement.engId) + 1 from Engagement engagement")
    Long findLastEngId();

    void deleteAllByEngId(Long engId);

    Engagement findFirstByEngId(Long engId);
}
