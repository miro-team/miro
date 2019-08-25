package miet.rooms.repository.jpa.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(schema = "locations", name = "schemes")
@SequenceGenerator(schema = "locations", name = "schemes", sequenceName = "locations.schemes_scheme_id_seq", allocationSize = 1)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@EqualsAndHashCode(of = {"name", "fileName", "floor", "building"})
public class Scheme {

    private Long id;
    private String name;
    private String fileName;
    private Long floor;
    private String building;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "locations.schemes_scheme_id_seq")
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    @Column(name = "file_name", nullable = false)
    public String getFileName() {
        return fileName;
    }

    @Column(name = "floor", nullable = false)
    public Long getFloor() {
        return floor;
    }

    @Column(name = "building", nullable = false)
    public String getBuilding() {
        return building;
    }
}
