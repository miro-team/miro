package miet.rooms.repository.jpa.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(schema = "schedule", name = "engage_types")
@SequenceGenerator(schema = "schedule", name = "engage_types", sequenceName = "schedule.engage_types_id_seq", allocationSize = 1)
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EngageType {

    @EqualsAndHashCode.Exclude
    private Long id;
    private String name;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "schedule.engage_types_id_seq")
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }
}
