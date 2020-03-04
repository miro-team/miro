package miet.rooms.repository.jpa.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(schema = "schedule", name = "events")
@SequenceGenerator(schema = "schedule", name = "events", sequenceName = "schedule.events_id_seq", allocationSize = 1)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class Event {

    @EqualsAndHashCode.Exclude
    private Long id;
    private LocalDate date;
    private Pair pair;
    private WeekType weekType;
    private Room room;
    private Long weekNum;
    private Long engId;
    private WeekDay weekDay;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "schedule.events_id_seq")
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
    @JoinColumn(name = "week_day", nullable = false)
    public WeekDay getWeekDay() {
        return weekDay;
    }

    @Column(name = "eng_id")
    public Long getEngId() {
        return engId;
    }
}
