package miet.rooms.repository.jpa.entity;

import javax.persistence.*;

@Entity
@Table(schema = "time_desc", name = "week_types")
@SequenceGenerator(schema = "time_desc", name = "week_types", sequenceName = "time_desc.week_types_week_type_id_seq", allocationSize = 1)
public class WeekType {
    private Long id;
    private String weekTypeName;
    private String weekTypeCode;
    private Long weekTypeCodeNum;
    private Long weeksToNext;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "time_desc.week_types_week_type_id_seq")
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "week_type_name", nullable = false)
    public String getWeekTypeName() {
        return weekTypeName;
    }

    public void setWeekTypeName(String weekTypeName) {
        this.weekTypeName = weekTypeName;
    }

    @Column(name = "week_type_code", nullable = false)
    public String getWeekTypeCode() {
        return weekTypeCode;
    }

    public void setWeekTypeCode(String weekTypeCode) {
        this.weekTypeCode = weekTypeCode;
    }

    @Column(name = "week_type_code_num", nullable = false)
    public Long getWeekTypeCodeNum() {
        return weekTypeCodeNum;
    }

    public void setWeekTypeCodeNum(Long weekTypeCodeNum) {
        this.weekTypeCodeNum = weekTypeCodeNum;
    }

    @Column(name = "weeks_to_next", nullable = false)
    public Long getWeeksToNext() {
        return weeksToNext;
    }

    public void setWeeksToNext(Long weeksToNext) {
        this.weeksToNext = weeksToNext;
    }
}
