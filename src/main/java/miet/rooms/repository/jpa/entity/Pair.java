package miet.rooms.repository.jpa.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(schema = "time_desc", name = "pairs")
@SequenceGenerator(schema = "time_desc", name = "pairs", sequenceName = "time_desc.pairs_pair_id_seq", allocationSize = 1)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class Pair {

    @EqualsAndHashCode.Exclude
    private Long id;
    private String name;
    private String timeFrom;
    private String timeTo;
    private Long order;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "time_desc.pairs_pair_id_seq")
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    @Column(name = "time_from", nullable = false)
    public String getTimeFrom() {
        return timeFrom;
    }

    @Column(name = "time_to", nullable = false)
    public String getTimeTo() {
        return timeTo;
    }

    @Column(name = "order", nullable = false)
    public Long getOrder() {
        return order;
    }
}
