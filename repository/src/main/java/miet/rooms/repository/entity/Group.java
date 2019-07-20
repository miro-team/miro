package miet.rooms.repository.entity;

import javax.persistence.*;

@Entity
@Table(schema = "data", name = "edu_groups")
@SequenceGenerator(schema = "data", name = "edu_groups", sequenceName = "data.groups_group_id_seq", allocationSize = 1)
public class Group {

    private Long id;
    private String name;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "data.groups_group_id_seq")
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
