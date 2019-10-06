package miet.rooms.repository.jpa.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(schema = "time_desc", name = "week_days")
@SequenceGenerator(schema = "time_desc", name = "week_days", sequenceName = "time_desc.week_day_id_seq", allocationSize = 1)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class WeekDay {

    @EqualsAndHashCode.Exclude
    private Long id;
    private String dayCode;
    private String dayName;
    private Integer orderNum;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "time_desc.week_day_id_seq")
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @Column(name = "day_code", nullable = false)
    public String getDayCode() {
        return dayCode;
    }

    @Column(name = "day_name", nullable = false)
    public String getDayName() {
        return dayName;
    }

    @Column(name = "order_num", nullable = false)
    public Integer getOrderNum() {
        return orderNum;
    }
}
