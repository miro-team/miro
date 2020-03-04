package miet.rooms.security.jpa.entity;


import lombok.*;

import javax.persistence.*;

@Entity
@Table(schema = "security", name = "user_roles")
@SequenceGenerator(schema = "security", name = "users", sequenceName = "security.user_roles_id_seq", allocationSize = 1)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class UserRole {
    @EqualsAndHashCode.Exclude
    private Long id;

    private String name;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "security.user_roles_id_seq")
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }
}
