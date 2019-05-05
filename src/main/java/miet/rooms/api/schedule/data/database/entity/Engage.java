package miet.rooms.api.schedule.data.database.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(schema = "schedule", name = "engages")
@SequenceGenerator(schema = "schedule", name = "engages", sequenceName = "schedule.engages_engage_id_seq", allocationSize = 1)
public class Engage {

    private Long id;
    private Group engagedBy;
    private Long engagedType;
    private AllData transferedFrom;
    private String teacherName;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "schedule.engages_engage_id_seq")
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "engagedBy")
    public Group getEngagedBy() {
        return engagedBy;
    }

    public void setEngagedBy(Group engagedBy) {
        this.engagedBy = engagedBy;
    }

    @Column(name = "engage_type", nullable = false)
    public Long getEngagedType() {
        return engagedType;
    }

    public void setEngagedType(Long engagedType) {
        this.engagedType = engagedType;
    }

    @OneToOne
    @JoinColumn(name = "transfered_from_room_id")
    public AllData getTransferedFrom() {
        return transferedFrom;
    }

    public void setTransferedFrom(AllData transferedFrom) {
        this.transferedFrom = transferedFrom;
    }

    @Column(name = "teacher_name", nullable = false)
    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
}
