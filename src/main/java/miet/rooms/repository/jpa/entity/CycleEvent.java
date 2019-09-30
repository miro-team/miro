package miet.rooms.repository.jpa.entity;

import javax.persistence.*;

@Entity
@Table(schema = "schedule", name = "cycle_events")
@SequenceGenerator(schema = "schedule", name = "cycle_events", sequenceName = "schedule.cycle_events_event_id_seq", allocationSize = 1)
public class CycleEvent {

    private Long id;
    private String weekType;
    private String groupId;
    private Long seqName;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "schedule.cycle_events_event_id_seq")
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "week_type", nullable = false)
    public String getWeekType() {
        return weekType;
    }

    public void setWeekType(String weekType) {
        this.weekType = weekType;
    }

    @Column(name = "group_id", nullable = false)
    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @Column(name = "seq_num", nullable = false)
    public Long getSeqName() {
        return seqName;
    }

    public void setSeqName(Long seqName) {
        this.seqName = seqName;
    }
}
