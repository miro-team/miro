package miet.rooms.repository.jpa.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(schema = "schedule", name = "engagements")
@SequenceGenerator(schema = "schedule", name = "engagements", sequenceName = "schedule.engagements_id_seq", allocationSize = 1)
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class Engagement {

    @EqualsAndHashCode.Exclude
    private Long id;
    private Discipline discipline;
    private Group group;
    private Teacher teacher;
    private Long engId;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "schedule.engagements_id_seq")
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @ManyToOne
    @JoinColumn(name = "discipline_id")
    public Discipline getDiscipline() {
        return discipline;
    }

    @ManyToOne
    @JoinColumn(name = "group_id")
    public Group getGroup() {
        return group;
    }

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    public Teacher getTeacher() {
        return teacher;
    }

    @Column(name = "eng_id")
    public Long getEngId() {
        return engId;
    }
}
