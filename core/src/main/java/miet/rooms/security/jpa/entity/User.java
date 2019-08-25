package miet.rooms.security.jpa.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(schema = "security", name = "users")
@SequenceGenerator(schema = "security", name = "users", sequenceName = "security.users_id_seq", allocationSize = 1)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class User {
    @EqualsAndHashCode.Exclude
    private Long id;
    private String userLogin;
    private String password;
    private String name;
    private String surname;
    private String patronymic;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "security.users_id_seq")
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @Column(name = "user_login", nullable = false)
    public String getUserLogin() {
        return userLogin;
    }

    @Column(name = "password", nullable = false)
    public String getPassword() {
        return password;
    }

    @Column(name = "user_name", nullable = false)
    public String getName() {
        return name;
    }

    @Column(name = "user_surname", nullable = false)
    public String getSurname() {
        return surname;
    }

    @Column(name = "user_patronymic", nullable = false)
    public String getPatronymic() {
        return patronymic;
    }
}
