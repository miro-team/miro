package miet.rooms.repository.jpa.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(schema = "schedule", name = "all_data")
@SequenceGenerator(schema = "schedule", name = "all_data", sequenceName = "schedule.all_data_data_id_seq", allocationSize = 1)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class AllData {

    @EqualsAndHashCode.Exclude
    private Long id;
    private LocalDate date;
    private Pair pair;
    private Group group;
    private WeekType weekType;
    private Room room;
    private Long weekNum;
    private EngageType engageType;
    private WeekDay weekDay;
    private Boolean isEngaged;
    private Reservation reservation;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "schedule.all_data_data_id_seq")
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @Column(name = "date", nullable = false)
    public LocalDate getDate() {
        return date;
    }

    @ManyToOne
    @JoinColumn(name = "pair_id")
    public Pair getPair() {
        return pair;
    }

    @ManyToOne
    @JoinColumn(name = "engaged_by_id")
    public Group getGroup() {
        return group;
    }

    @ManyToOne
    @JoinColumn(name = "week_type")
    public WeekType getWeekType() {
        return weekType;
    }

    @ManyToOne
    @JoinColumn(name = "room_id")
    public Room getRoom() {
        return room;
    }

    @Column(name = "week_num", nullable = false)
    public Long getWeekNum() {
        return weekNum;
    }

    @ManyToOne
    @JoinColumn(name = "engage_type_id", referencedColumnName = "engage_type_id")
    public EngageType getEngageType() {
        return engageType;
    }

    @ManyToOne
    @JoinColumn(name = "week_day")
    public WeekDay getWeekDay() {
        return weekDay;
    }

    @Column(name = "is_engaged", nullable = false)
    public Boolean getEngaged() {
        return isEngaged;
    }

    @ManyToOne
    @JoinColumn(name = "reservation_id")
    public Reservation getReservation() {
        return reservation;
    }

    public void setEngaged(Boolean engaged) {
        isEngaged = engaged;
    }
}
