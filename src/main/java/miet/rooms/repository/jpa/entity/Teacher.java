package miet.rooms.repository.jpa.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(schema = "schedule", name = "teachers")
@SequenceGenerator(schema = "schedule", name = "teachers", sequenceName = "schedule.teachers_id_seq", allocationSize = 1)
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class Teacher {

    @EqualsAndHashCode.Exclude
    private Long id;
    private Long name;
    private Long surname;
    private Long patronymic;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "schedule.teachers_id_seq")
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @Column(name = "name")
    public Long getName() {
        return name;
    }

    @Column(name = "surname")
    public Long getSurname() {
        return surname;
    }

    @Column(name = "patronymic")
    public Long getPatronymic() {
        return patronymic;
    }
}
