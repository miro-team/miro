package miet.rooms.repository.jpa.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(schema = "locations", name = "rooms")
@SequenceGenerator(schema = "locations", name = "rooms", sequenceName = "locations.rooms_room_id_seq", allocationSize = 1)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class Room {

    @EqualsAndHashCode.Exclude
    private Long id;
    private String name;
    private Long capacity;
    private Scheme scheme;
    private Integer schemeMapping;
    private RoomType roomType;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "locations.rooms_room_id_seq")
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    @Column(name = "capacity", nullable = false)
    public Long getCapacity() {
        return capacity;
    }

    @OneToOne
    @JoinColumn(name = "scheme_id")
    public Scheme getScheme() {
        return scheme;
    }

    @Column(name = "scheme_mapping", nullable = false)
    public Integer getSchemeMapping() {
        return schemeMapping;
    }

    @OneToOne
    @JoinColumn(name = "room_type_id")
    public RoomType getRoomType() {
        return roomType;
    }
}
