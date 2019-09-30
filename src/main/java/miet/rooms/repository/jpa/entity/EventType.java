package miet.rooms.repository.jpa.entity;

import javax.persistence.*;

@Entity
@Table(schema = "schedule", name = "event_types")
@SequenceGenerator(schema = "schedule", name = "event_types", sequenceName = "schedule.event_types_id_seq", allocationSize = 1)
public class EventType {
    private Long id;
    private String name;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "schedule.event_types_id_seq")
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
