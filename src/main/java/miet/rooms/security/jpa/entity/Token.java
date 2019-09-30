package miet.rooms.security.jpa.entity;

import javax.persistence.*;

@Entity
@Table(schema = "security", name = "tokens")
@SequenceGenerator(schema = "security", name = "users", sequenceName = "security.tokens_id_seq", allocationSize = 1)
public class Token {
    private Long id;
    private String token;
    private User user;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "security.tokens_id_seq")
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "token", nullable = false)
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
