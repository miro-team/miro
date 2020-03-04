package miet.rooms.repository.jpa.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(schema = "data", name = "edu_groups")
@SequenceGenerator(schema = "data", name = "edu_groups", sequenceName = "data.groups_group_id_seq", allocationSize = 1)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class Group {

    @EqualsAndHashCode.Exclude
    private Long id;
    private String name;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "data.groups_group_id_seq")
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }
}
