package miet.rooms.repository.jpa.entity;

import lombok.*;
import miet.rooms.security.jpa.entity.User;

import javax.persistence.*;
import java.time.LocalDate;

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
    private EventType eventType;
    private User createdBy;
    private LocalDate createdAt;
    private Group group;
    private String teacher;
    private Periodicity periodicity;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "schedule.reservations_id_seq")
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @ManyToOne
    @JoinColumn(name = "event_type_id")
    public EventType getEventType() {
        return eventType;
    }

    @ManyToOne
    @JoinColumn(name = "created_by")
    public User getCreatedBy() {
        return createdBy;
    }

    @Column(name = "created_at", nullable = false)
    public LocalDate getCreatedAt() {
        return createdAt;
    }

    @ManyToOne
    @JoinColumn(name = "group_id")
    public Group getGroup() {
        return group;
    }

    @Column(name = "teacher", nullable = false)
    public String getTeacher() {
        return teacher;
    }

    @ManyToOne
    @JoinColumn(name = "periodicity")
    public Periodicity getPeriodicity() {
        return periodicity;
    }
}
