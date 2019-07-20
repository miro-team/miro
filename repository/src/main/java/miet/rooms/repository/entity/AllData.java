package miet.rooms.repository.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(schema = "schedule", name = "all_data")
@SequenceGenerator(schema = "schedule", name = "all_data", sequenceName = "schedule.all_data_data_id_seq", allocationSize = 1)
public class AllData {

    private Long id;
    private LocalDate date;
    private Pair pair;
    private Group group;
    private Long weekType;
    private Room room;
    private Long weekNum;
    private EngageType engageType;
    private Long weekDay;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "schedule.all_data_data_id_seq")
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "date", nullable = false)
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @ManyToOne
    @JoinColumn(name = "pair_id")
    public Pair getPair() {
        return pair;
    }

    public void setPair(Pair pair) {
        this.pair = pair;
    }

    @ManyToOne
    @JoinColumn(name = "engaged_by_id")
    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @Column(name = "week_type", nullable = false)
    public Long getWeekType() {
        return weekType;
    }

    public void setWeekType(Long weekType) {
        this.weekType = weekType;
    }

    @ManyToOne
    @JoinColumn(name = "room_id")
    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    @Column(name = "week_num", nullable = false)
    public Long getWeekNum() {
        return weekNum;
    }

    public void setWeekNum(Long weekNum) {
        this.weekNum = weekNum;
    }

    @ManyToOne
    @JoinColumn(name = "engage_type_id", referencedColumnName = "engage_type_id")
    public EngageType getEngageType() {
        return engageType;
    }

    public void setEngageType(EngageType engageType) {
        this.engageType = engageType;
    }

    @Column(name = "week_day", nullable = false)
    public Long getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(Long weekDay) {
        this.weekDay = weekDay;
    }
}
