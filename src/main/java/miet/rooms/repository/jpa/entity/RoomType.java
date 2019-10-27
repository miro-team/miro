package miet.rooms.repository.jpa.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(schema = "locations", name = "room_type")
@SequenceGenerator(schema = "locations", name = "room_type", sequenceName = "locations.room_types_room_type_id_seq", allocationSize = 1)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomType {
    private Long id;
    private String name;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "locations.room_types_room_type_id_seq")
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
