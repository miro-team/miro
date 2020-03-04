package miet.rooms.repository.jpa.entity;

import miet.rooms.api.util.types.IntArrayType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;

@Entity
@Table(schema = "time_desc", name = "periodicities")
@SequenceGenerator(schema = "time_desc", name = "periodicities", sequenceName = "time_desc.periodicities_id_seq", allocationSize = 1)
@TypeDef(
        name = "int-array",
        typeClass = IntArrayType.class
)
public class Periodicity {
    private Long id;
    private Integer[] weekTypes;
    private String name;
    private Long weeksToNext;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "time_desc.periodicities_id_seq")
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Type(type = "int-array")
    @Column(name = "week_types", nullable = false)
    public Integer[] getWeekTypes() {
        return weekTypes;
    }

    public void setWeekTypes(Integer[] weekTypes) {
        this.weekTypes = weekTypes;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "weeks_to_next", nullable = false)
    public Long getWeeksToNext() {
        return weeksToNext;
    }

    public void setWeeksToNext(Long weeksToNext) {
        this.weeksToNext = weeksToNext;
    }
}
