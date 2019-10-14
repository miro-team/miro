package miet.rooms.repository.jpa.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(schema = "schedule", name = "teachers")
@SequenceGenerator(schema = "schedule", name = "teachers", sequenceName = "schedule.teachers_id_seq", allocationSize = 1)
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Builder
public class Teacher {

    @EqualsAndHashCode.Exclude
    private Long id;
    private String name;
    private String surname;
    private String patronymic;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "schedule.teachers_id_seq")
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @Column(name = "teacher_name")
    public String getName() {
        return name;
    }

    @Column(name = "teacher_surname")
    public String getSurname() {
        return surname;
    }

    @Column(name = "teacher_patronymic")
    public String getPatronymic() {
        return patronymic;
    }
}
