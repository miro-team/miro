package miet.rooms.security.jpa.entity;

import javax.persistence.*;

@Entity
@Table(schema = "security", name = "users")
@SequenceGenerator(schema = "security", name = "users", sequenceName = "security.users_id_seq", allocationSize = 1)
public class User {
    private Long id;
    private String username;
    private String password;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "security.users_id_seq")
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "username", nullable = false)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "password", nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
