package miet.rooms.api.schedule.data.database.entity;

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
    private String weekType;
    private Room room;

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
    public String getWeekType() {
        return weekType;
    }

    public void setWeekType(String weekType) {
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
}
