package miet.rooms.repository.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(schema = "schedule", name = "engage_type")
@SequenceGenerator(schema = "schedule", name = "engage_type", sequenceName = "schedule.engage_types_engage_type_id_seq", allocationSize = 1)
public class EngageType implements Serializable {

    private Long id;
    private Long engageTypeId;
    private String description;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "schedule.engage_types_engage_type_id_seq")
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "engage_type_id", nullable = false)
    public Long getEngageTypeId() {
        return engageTypeId;
    }

    public void setEngageTypeId(Long engageTypeId) {
        this.engageTypeId = engageTypeId;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
