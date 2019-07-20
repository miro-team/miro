package miet.rooms.repository.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(schema = "schedule", name = "engages")
@SequenceGenerator(schema = "schedule", name = "engages", sequenceName = "schedule.engages_engage_id_seq", allocationSize = 1)
public class Engage {

    private Long id;
    private Group engagedBy;
    private EngageType engagedType;
    private AllData transferredFrom;
    private String teacherName;
    private LocalDateTime insertDate;
    private AllData allData;

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
    @JoinColumn(name = "engaged_by_id")
    public Group getEngagedBy() {
        return engagedBy;
    }

    public void setEngagedBy(Group engagedBy) {
        this.engagedBy = engagedBy;
    }

    @ManyToOne
    @JoinColumn(name = "engage_type_id", referencedColumnName = "engage_type_id")
    public EngageType getEngagedType() {
        return engagedType;
    }

    public void setEngagedType(EngageType engagedType) {
        this.engagedType = engagedType;
    }

    @ManyToOne
    @JoinColumn(name = "transferred_from_all_data_id")
    public AllData getTransferredFrom() {
        return transferredFrom;
    }

    public void setTransferredFrom(AllData transferredFrom) {
        this.transferredFrom = transferredFrom;
    }

    @Column(name = "teacher_name", nullable = false)
    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    @Column(name = "insert_date", nullable = false)
    public LocalDateTime getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(LocalDateTime insertDate) {
        this.insertDate = insertDate;
    }

    @ManyToOne
    @JoinColumn(name = "all_data_id")
    public AllData getAllData() {
        return allData;
    }

    public void setAllData(AllData allData) {
        this.allData = allData;
    }
}
