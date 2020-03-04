package miet.rooms.repository.jpa.entity;

import lombok.*;
import miet.rooms.security.jpa.entity.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(schema = "schedule", name = "reservations")
@SequenceGenerator(schema = "schedule", name = "reservations", sequenceName = "schedule.reservations_id_seq", allocationSize = 1)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class Reservation {

    @EqualsAndHashCode.Exclude
    private Long id;
    private Long engId;
    private User createdBy;
    private LocalDateTime createdAt;
    private EngageType engageType;
    private Discipline discipline;
    private Group group;
    private Teacher teacher;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "schedule.reservations_id_seq")
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @Column(name = "eng_id", nullable = false)
    public Long getEngId() {
        return engId;
    }

    @ManyToOne
    @JoinColumn(name = "created_by")
    public User getCreatedBy() {
        return createdBy;
    }

    @Column(name = "created_at", nullable = false)
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @ManyToOne
    @JoinColumn(name = "engage_type_id")
    public EngageType getEngageType() {
        return engageType;
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
}
