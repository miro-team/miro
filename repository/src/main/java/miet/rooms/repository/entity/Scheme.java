package miet.rooms.repository.entity;

import javax.persistence.*;

@Entity
@Table(schema = "locations", name = "schemes")
@SequenceGenerator(schema = "locations", name = "schemes", sequenceName = "locations.schemes_scheme_id_seq", allocationSize = 1)
public class Scheme {

    private Long id;
    private String name;
    private String fileName;
    private Long floor;
    private Long building;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "locations.schemes_scheme_id_seq")
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

    @Column(name = "file_name", nullable = false)
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Column(name = "floor", nullable = false)
    public Long getFloor() {
        return floor;
    }

    public void setFloor(Long floor) {
        this.floor = floor;
    }

    @Column(name = "building", nullable = false)
    public Long getBuilding() {
        return building;
    }

    public void setBuilding(Long building) {
        this.building = building;
    }
}
