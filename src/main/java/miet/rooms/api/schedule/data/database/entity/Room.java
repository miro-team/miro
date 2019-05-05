package miet.rooms.api.schedule.data.database.entity;

import javax.persistence.*;

@Entity
@Table(schema = "locations", name = "rooms")
@SequenceGenerator(schema = "locations", name = "rooms", sequenceName = "locations.rooms_room_id_seq", allocationSize = 1)
public class Room {

    private Long id;
    private String name;
    private Integer capacity;
    private String type;
    private Scheme scheme;
    private Integer schemeMapping;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "locations.rooms_room_id_seq")
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

    @Column(name = "capacity", nullable = false)
    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    @Column(name = "type", nullable = false)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @ManyToOne
    @JoinColumn(name = "scheme_id")
    public Scheme getScheme() {
        return scheme;
    }

    public void setScheme(Scheme scheme) {
        this.scheme = scheme;
    }

    @Column(name = "schemeMapping", nullable = false)
    public Integer getSchemeMapping() {
        return schemeMapping;
    }

    public void setSchemeMapping(Integer schemeMapping) {
        this.schemeMapping = schemeMapping;
    }
}
