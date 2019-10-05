package miet.rooms.repository.jpa.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(schema = "schedule", name = "engage_types")
@SequenceGenerator(schema = "schedule", name = "engage_types", sequenceName = "schedule.engage_types_engage_type_id_seq", allocationSize = 1)
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class Discipline {

    @EqualsAndHashCode.Exclude
    private Long id;
    private String title;
    private EngageType engageType;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "schedule.engage_types_engage_type_id_seq")
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    @ManyToOne
    @JoinColumn(name = "eng_id")
    public EngageType getEngageType() {
        return engageType;
    }
}
