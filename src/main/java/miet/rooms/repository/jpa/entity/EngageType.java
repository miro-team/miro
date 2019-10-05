package miet.rooms.repository.jpa.entity;

import lombok.EqualsAndHashCode;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(schema = "schedule", name = "event_types")
@SequenceGenerator(schema = "schedule", name = "event_types", sequenceName = "schedule.event_types_id_seq", allocationSize = 1)
@Setter
public class EngageType {

    @EqualsAndHashCode.Exclude
    private Long id;
    private String name;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "schedule.event_types_id_seq")
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }
}
