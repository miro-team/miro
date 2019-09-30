package miet.rooms.repository.jpa.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(schema = "time_desc", name = "week_days")
@SequenceGenerator(schema = "time_desc", name = "week_days", sequenceName = "time_desc.week_days_id_seq", allocationSize = 1)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class WeekDay {

    @EqualsAndHashCode.Exclude
    private Long id;
    private String weekDayName;
    private Integer order;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "time_desc.week_days_id_seq")
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @Column(name = "week_day_name", nullable = false)
    public String getWeekDayName() {
        return weekDayName;
    }

    @Column(name = "order", nullable = false)
    public Integer getOrder() {
        return order;
    }
}
