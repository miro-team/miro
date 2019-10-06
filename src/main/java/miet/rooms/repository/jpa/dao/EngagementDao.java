package miet.rooms.repository.jpa.dao;

import miet.rooms.repository.jpa.entity.Engagement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EngagementDao extends JpaRepository<Engagement, Long> {
}
