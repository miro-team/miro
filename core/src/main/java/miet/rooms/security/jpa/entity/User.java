package miet.rooms.security.jpa.entity;

import javax.persistence.*;

@Entity
@Table(schema = "security", name = "users")
@SequenceGenerator(schema = "security", name = "users", sequenceName = "security.users_id_seq", allocationSize = 1)
public class User {
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

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "user_login", nullable = false)
    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    @Column(name = "password", nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "user_name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "user_surname", nullable = false)
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Column(name = "user_patronymic", nullable = false)
    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }
}
