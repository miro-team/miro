package miet.rooms.repository.entity;

import javax.persistence.*;

@Entity
@Table(schema = "time_desc", name = "week_days")
@SequenceGenerator(schema = "time_desc", name = "week_days", sequenceName = "time_desc.week_days_id_seq", allocationSize = 1)
public class WeekDay {
    private Long id;
    private String weekDayName;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "time_desc.week_days_id_seq")
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "week_day_name", nullable = false)
    public String getWeekDayName() {
        return weekDayName;
    }

    public void setWeekDayName(String weekDayName) {
        this.weekDayName = weekDayName;
    }
}
