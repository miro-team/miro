package miet.rooms.repository.jpa.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(schema = "schedule", name = "discipline")
@SequenceGenerator(schema = "schedule", name = "discipline", sequenceName = "schedule.discipline_id_seq", allocationSize = 1)
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Builder
public class Discipline {

    @EqualsAndHashCode.Exclude
    private Long id;
    private String title;
    private EngageType disType;
    private String code;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "schedule.discipline_id_seq")
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    @ManyToOne
    @JoinColumn(name = "dis_type_id")
    public EngageType getDisType() {
        return disType;
    }

    @Column(name = "code")
    public String getCode() {
        return code;
    }
}
